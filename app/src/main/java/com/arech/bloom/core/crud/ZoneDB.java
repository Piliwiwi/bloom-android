package com.arech.bloom.core.crud;

import com.arech.bloom.models.Switch;
import com.arech.bloom.models.Zone;

import org.json.JSONObject;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ZoneDB {
    public static void save(final Zone zone) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(zone);
            }
        });
        realm.close();
    }

    public static void add(final List<Zone> zones) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for(Zone zone: zones) {
                    realm.copyToRealmOrUpdate(zone);
                }
            }
        });
        realm.close();
    }

    public static void update(final JSONObject zone) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createOrUpdateObjectFromJson(Zone.class, zone);
            }
        });
        realm.close();
    }

    public static void setCollapse(final String id, final boolean collapse) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Zone zone = realm.where(Zone.class).equalTo("_id", id).findFirst();
                assert zone != null;
                zone.setCollapsed(collapse);
            }
        });
        realm.close();
    }

    //Get
    public final static RealmResults<Zone> getAll(String sector) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Zone> zones = realm.where(Zone.class).equalTo("sector", sector).findAll();
        realm.close();
        return zones;
    }

    public final static RealmResults<Zone> getAll() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Zone> zones = realm.where(Zone.class).findAll();
        realm.close();
        return zones;
    }


    public final static Zone getForId(String id) {
        Realm realm = Realm.getDefaultInstance();
        Zone zone = realm.where(Zone.class).equalTo("_id", id).findFirst();
        realm.close();
        return zone;
    }

    public static void setActive(final String id, final boolean isActive) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Zone zone = realm.where(Zone.class).equalTo("_id", id).findFirst();
                assert zone != null;
                zone.setActive(isActive);
            }
        });
        realm.close();
    }
}
