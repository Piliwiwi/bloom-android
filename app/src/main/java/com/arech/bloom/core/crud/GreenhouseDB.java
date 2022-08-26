package com.arech.bloom.core.crud;

import com.arech.bloom.models.Greenhouse;

import org.json.JSONObject;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Pili Arancibia on 8/18/19
 */
public class GreenhouseDB {
    //Create or Update
    public final static void add(final Greenhouse greenhouse) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(greenhouse);
            }
        });
        realm.close();
    }

    public final static void add(final List<Greenhouse> greenhouses) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for(Greenhouse greenhouse: greenhouses) {
                    realm.copyToRealmOrUpdate(greenhouse);
                }
            }
        });
        realm.close();
    }

    public final static void update(final JSONObject greenhouse) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createOrUpdateObjectFromJson(Greenhouse.class, greenhouse);
            }
        });
        realm.close();
    }

    //Get
    public final static RealmResults<Greenhouse> getAll() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Greenhouse> greenhouses = realm.where(Greenhouse.class).findAll();
        return greenhouses;
    }

    public final static Greenhouse getForId(String id) {
        Realm realm = Realm.getDefaultInstance();
        Greenhouse greenhouse = realm.where(Greenhouse.class).equalTo("_id", id).findFirst();
        return greenhouse;
    }
}
