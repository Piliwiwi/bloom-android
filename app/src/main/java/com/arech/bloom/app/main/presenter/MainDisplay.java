package com.arech.bloom.app.main.presenter;

import com.arech.bloom.R;
import com.arech.bloom.app.main.MainActivity;
import com.arech.bloom.core.crud.UserDB;
import com.arech.bloom.models.User;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

/**
 * Created by Pili Arancibia on 5/11/19
 */
public class MainDisplay {

    public static void displayUserConfig(MainActivity mainActivity) {
        User me = UserDB.getMe();
        if(me != null && mainActivity.getNavTitle() != null && mainActivity.getNavSubtitle() != null) {
            mainActivity.getNavTitle().setText(me.getName());
            mainActivity.getNavSubtitle().setText(me.getEmail());
        }
    }

    public static void setNavigation(MainActivity mainActivity) {
        Toolbar toolbar = (Toolbar) mainActivity.findViewById(R.id.toolbar);
        mainActivity.setSupportActionBar(toolbar);
        toolbar.setTitle("Bloom");

//        FloatingActionButton fab = (FloatingActionButton) mainActivity.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) mainActivity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                mainActivity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) mainActivity.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(mainActivity);
        displayUserConfig(mainActivity);

    }

}
