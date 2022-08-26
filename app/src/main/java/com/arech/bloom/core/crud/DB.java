package com.arech.bloom.core.crud;

import android.content.Context;

import com.arech.bloom.config.Config;
import com.arech.bloom.core.BloomMigration;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Pili Arancibia on 5/11/19
 */
public class DB {
    static private Map<String, RealmConfiguration> realmConfig = new HashMap<>();

    //Delete
    public final static void deleteAll() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
    }

    static Realm configRealm(Context context, String file) {
        return Realm.getInstance(getConfig(context, file));
    }

    static private RealmConfiguration getConfig(Context context, String file) {
        if (realmConfig.containsKey(file)) {
            return realmConfig.get(file);
        }

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(file)
                .schemaVersion(Config.INSTANCE.getDATABASE_SCHEMA_VERSION())
                .migration(new BloomMigration())
                .deleteRealmIfMigrationNeeded()
                .build();

        realmConfig.put(file, config);
        return config;
    }

    /* TODO: put here all generic transactions */

}
