package com.arech.bloom.core.crud;

import com.arech.bloom.models.utils.Bed;

import org.json.JSONObject;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class BedDB {
    //Create or Update
    public static void save(final Bed bed) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(bed);
            }
        });
        realm.close();
    }

    public static void add(final List<Bed> beds) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for(Bed bed: beds) {
                    realm.copyToRealmOrUpdate(bed);
                }
            }
        });
        realm.close();
    }

    public static void update(final JSONObject bed) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createOrUpdateObjectFromJson(Bed.class, bed);
            }
        });
        realm.close();
    }

    public static void setCollapse(final String id, final boolean collapse) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Bed bed = realm.where(Bed.class).equalTo("_id", id).findFirst();
                assert bed != null;
                bed.setCollapsed(collapse);
            }
        });
        realm.close();
    }

    //Get
    public final static RealmResults<Bed> getAll(String sector) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Bed> beds = realm.where(Bed.class).equalTo("sectorId", sector).findAll();
        return beds;
    }

    public final static RealmResults<Bed> getAll() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Bed> beds = realm.where(Bed.class).findAll();
        return beds;
    }


    public final static Bed getForId(String id) {
        Realm realm = Realm.getDefaultInstance();
        Bed bed = realm.where(Bed.class).equalTo("_id", id).findFirst();
        return bed;
    }
}
