package com.arech.bloom.app.main.presenter;

import android.content.Context;

import com.arech.bloom.app.main.MainActivity;
import com.arech.bloom.core.crud.DB;
import com.arech.bloom.core.crud.UserPreferencesDB;
import com.arech.bloom.network.call.UserCall;
import com.arech.bloom.network.callbacks.BloomCallback;
import com.arech.bloom.utils.NavigationHelper;
import com.arech.bloom.utils.SocketInstance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

/**
 * Created by Pili Arancibia on 5/11/19
 */
public class MainNav {
    public static boolean verifySession(final MainActivity mainActivity) {
        if (checkLogin(mainActivity)) {
            return true;
        } else {
            logout(mainActivity);
            return false;
        }
    }
    
    public static void logout(MainActivity mainActivity) {
        UserCall.logout(new BloomCallback() {
            @Override
            public void onSuccess(@Nullable Object value, int status) {
                System.out.println("Logout exitoso!");
            }

            @Override
            public void onServerError(int status) {
                System.out.println("Logout Server error!");
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                System.out.println("Logout error!");
            }
        });

        DB.deleteAll();
        SocketInstance.Companion    .getSocket().disconnect(mainActivity);
        NavigationHelper.navToLanding(mainActivity);
        ActivityCompat.finishAffinity(mainActivity);
        mainActivity.finish();
    }

    public static boolean checkLogin(Context context) {
        if(UserPreferencesDB.get() == null || UserPreferencesDB.get().getUserId().equals("")) {
            NavigationHelper.navToLanding(context);
            return false;
        }
        return true;
    }
}
