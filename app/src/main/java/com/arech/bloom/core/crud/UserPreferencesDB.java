package com.arech.bloom.core.crud;

import com.arech.bloom.config.Resources;
import com.arech.bloom.models.Node;
import com.arech.bloom.models.utils.UserPreferences;

import io.realm.Realm;

/**
 * Created by Pili Arancibia on 5/11/19
 */
public class UserPreferencesDB {

    //Create or update
    public final static void save(final UserPreferences userPreferences) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(userPreferences);
            }
        });
        realm.close();
    }

    //Get
    public final static UserPreferences get() {
        Realm realm = Realm.getDefaultInstance();
        UserPreferences userPreferences = realm.where(UserPreferences.class).equalTo("identifier", Resources.CURRENT_USER_IDENTIFIER).findFirst();

        return userPreferences;
    }

    public final static String getId() {
        Realm realm = Realm.getDefaultInstance();
        UserPreferences userPreferences = realm.where(UserPreferences.class).equalTo("identifier", Resources.CURRENT_USER_IDENTIFIER).findFirst();
        if (userPreferences == null) {
            return "";
        }
        return userPreferences.getUserId();
    }

    public final static String getToken() {
        Realm realm = Realm.getDefaultInstance();
        UserPreferences userPreferences = realm.where(UserPreferences.class).equalTo("identifier", Resources.CURRENT_USER_IDENTIFIER).findFirst();
        if (userPreferences == null) {
            return "";
        }
        return userPreferences.getToken();
    }

    public final static boolean hasBedView() {
        Realm realm = Realm.getDefaultInstance();
        UserPreferences userPreferences = realm.where(UserPreferences.class).equalTo("identifier", Resources.CURRENT_USER_IDENTIFIER).findFirst();
        if (userPreferences == null) {
            return false;
        }
        return userPreferences.isHasBedView();
    }

    public static void setHasBedView(final boolean hasBedView) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UserPreferences pref = realm.where(UserPreferences.class).findFirst();
                assert pref != null;
                pref.setHasBedView(hasBedView);
            }
        });
        realm.close();
    }

}
