package com.arech.bloom.app.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.arech.bloom.R;
import com.arech.bloom.app.zone.ZoneFragment;
import com.arech.bloom.app.fields.FieldFragment;
import com.arech.bloom.app.greenhouse.GreenhouseFragment;
import com.arech.bloom.app.home.HomeFragment;
import com.arech.bloom.app.main.presenter.MainDisplay;
import com.arech.bloom.app.main.presenter.MainNav;
import com.arech.bloom.app.node.NodeFragment;
import com.arech.bloom.app.sector.SectorFragment;
import com.arech.bloom.app.settings.SettingsFragment;
import com.arech.bloom.app.switches.SwitchFragment;
import com.arech.bloom.app.zone.presenter.model.PresenterZone;
import com.arech.bloom.base.FragmentInterface;
import com.arech.bloom.core.crud.CompanyDB;
import com.arech.bloom.core.crud.UserPreferencesDB;
import com.arech.bloom.manager.PersonalDataManager;
import com.arech.bloom.models.Company;
import com.arech.bloom.models.Zone;
import com.arech.bloom.network.ConnectionLiveData;
import com.arech.bloom.network.call.UserCall;
import com.arech.bloom.network.callbacks.BloomCallback;
import com.arech.bloom.network.req.DeviceRegistrationRequest;
import com.arech.bloom.utils.SocketInstance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView navTitle;
    TextView navSubtitle;
    ImageView socketSync;
    private Snackbar mSnackBar = null;
    Context context = this;
    Toolbar toolbar;
    String sectorId = null;
    MutableLiveData<Boolean> isOnline = new MutableLiveData<>();

    @Override
    protected void onStart() {
        super.onStart();
        connectSocket();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View header = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);
        toolbar = findViewById(R.id.toolbar);

        navSubtitle = header.findViewById(R.id.nav_header_subtitle);
        navTitle = header.findViewById(R.id.nav_header_title);
        socketSync = toolbar.findViewById(R.id.toolbar_socket_activity);

        socketSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Sincronizando...", Snackbar.LENGTH_LONG).show();
                reSync();
            }
        });

        mSnackBar = null;

        obtainFCMToken();

        if (MainNav.verifySession(this)) {
            connectSocket();

            new Thread(new Runnable() {
                public void run() {
                    PersonalDataManager.downloadPersonalInformation(context);
                }
            }).start();

            connectionDetect();
            MainDisplay.setNavigation(this);
            goToHome();
        }

    }

    private void obtainFCMToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("PILI", "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        //TODO: Send to server
                        DeviceRegistrationRequest request = new DeviceRegistrationRequest(token);
                        UserCall.sendDeviceRegistration(request, new BloomCallback() {
                            @Override
                            public void onSuccess(@Nullable Object value, int status) {
                                if (status == 200) {
                                    System.out.println("TOKEN REGISTRADO!");
                                } else {
                                    System.out.println("ERROR al registrar el token!");
                                }
                            }

                            @Override
                            public void onServerError(int status) {
                                System.out.println("ERROR del SERVER al registrar el token!");
                            }

                            @Override
                            public void onError(@NonNull Throwable throwable) {
                                System.out.println("ERROR 2 al registrar el token!");
                            }
                        });
                    }
                });
    }


    private void reSync() {
        connectSocket();
        new Thread(new Runnable() {
            public void run() {
                PersonalDataManager.downloadPersonalInformation(context);
            }
        }).start();
        connectionDetect();
    }

    private void connectSocket() {
        SocketInstance.Companion.getSocket().connect(this);
    }

    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    private void connectionDetect() {
        // TODO: posibly memory leak ?
        ConnectionLiveData connectionLiveData = new ConnectionLiveData(getApplicationContext());
        connectionLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean connection) {
                isOnline.setValue(connection);
                if (connection) {
                    if (mSnackBar != null) {
                        mSnackBar.dismiss();
                        PersonalDataManager.downloadPersonalInformation(context);
                    }
                } else {
                    mSnackBar = Snackbar.make(findViewById(R.id.main_content_area), "No hay conexión a Internet", Snackbar.LENGTH_LONG);
                    mSnackBar.setDuration(BaseTransientBottomBar.LENGTH_INDEFINITE);
                    mSnackBar.show();
                }
            }
        });
    }

    public void dismissKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void processCustoms() {
        Company company = CompanyDB.getMyCompany();
        if (company != null && company.getConfig() != null && company.getConfig().getCustoms() != null) {
            for (String custom : company.getConfig().getCustoms()) {
                if (custom.equals("node-view")) {
                    UserPreferencesDB.setHasBedView(false);
                } else {
                    UserPreferencesDB.setHasBedView(true);
                }
            }
            invalidateOptionsMenu();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        obtainFCMToken();
        reSync();
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            manager.popBackStackImmediate();
            if (manager.getBackStackEntryCount() == 0) {
                MainDisplay.setNavigation(this);
            }
        } else {
            super.onBackPressed();
        }

        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            goToGSettings();
            invalidateOptionsMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_settings) {
            goToGSettings();
        } else if (id == R.id.nav_logout) {
            MainNav.logout(this);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void goToHome() {
        Fragment newFragment = HomeFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content_area, newFragment, "home-fragment");
        transaction.commit();
    }

    public void goToFields() {
        Fragment newFragment = FieldFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content_area, newFragment, "field-fragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void goToGreenhouses() {
        Fragment newFragment = GreenhouseFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content_area, newFragment, "greenhouse-fragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void goToSectors(String greenhouseId) {
        Fragment newFragment = SectorFragment.newInstance();
        SectorFragment sectorFragment = (SectorFragment) newFragment;
        sectorFragment.setGreenhouse(greenhouseId);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content_area, sectorFragment, "sector-fragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void goToZones(String sectorId, boolean fromOptions) {
        this.sectorId = sectorId;
        Fragment newFragment = ZoneFragment.newInstance();
        ZoneFragment zoneFragment = (ZoneFragment) newFragment;
        zoneFragment.setSector(sectorId);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content_area, zoneFragment, "zone-fragment");
        transaction.addToBackStack(null);
        transaction.commit();
        invalidateOptionsMenu();
    }

    public void goToSwitches(String nodeId) {
        Fragment newFragment = SwitchFragment.newInstance();
        SwitchFragment switchFragment = (SwitchFragment) newFragment;
        switchFragment.setNode(nodeId);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content_area, switchFragment, "switch-fragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void goToZoneSwitches(PresenterZone zone) {
        Fragment newFragment = SwitchFragment.newInstance();
        SwitchFragment switchFragment = (SwitchFragment) newFragment;
        switchFragment.setZone(zone);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content_area, switchFragment, "switch-zone-fragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void goToGSettings() {
        setupBar("Configuración", null, R.drawable.ic_arrow_back_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.onBackPressed();
            }
        });
        Fragment newFragment = SettingsFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content_area, newFragment, "settings-fragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void setupBar(String title, String subtitle, Integer navIconRes, View.OnClickListener navListener) {
        setupBar(title, subtitle, navIconRes, navListener, null);
    }

    public void setupBar(String title, String subtitle, Integer navIconRes, View.OnClickListener navListener, View.OnClickListener titleListener) {
        setSupportActionBar(toolbar);
        if (navIconRes != null) toolbar.setNavigationIcon(navIconRes);
        if (navListener != null) toolbar.setNavigationOnClickListener(navListener);
        toolbar.setTitle(title);
        if (subtitle != null) toolbar.setSubtitle(subtitle);
        toolbar.setOnClickListener(titleListener);
    }

    public void updateFragmentUI() {
        if (this.getVisibleFragment() != null && this.getVisibleFragment() instanceof FragmentInterface) {
            FragmentInterface fragmentInterface = (FragmentInterface) this.getVisibleFragment();
            fragmentInterface.updateUI();
        }
    }


    //Getters
    public TextView getNavTitle() {
        return navTitle;
    }

    public TextView getNavSubtitle() {
        return navSubtitle;
    }

    public ImageView getSocketSync() {
        return socketSync;
    }

    public void setSocketSyncResource(final boolean connected) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (connected) socketSync.setImageResource(R.drawable.ic_sync_on);
                else socketSync.setImageResource(R.drawable.ic_sync_off);
            }
        });
    }
}
