package com.arech.bloom.core.crud;

import com.arech.bloom.models.User;

import io.realm.Realm;

/**
 * Created by Pili Arancibia on 5/11/19
 */
public class UserDB {

    //Create or Update
    public final static void add(final User user) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(user);
            }
        });
        realm.close();
    }

    //Get
    public final static User getMe() {
        Realm realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).equalTo("_id", UserPreferencesDB.getId()).findFirst();

        return user;
    }

}
