package com.arech.bloom.core.crud;

import com.arech.bloom.models.Sector;

import org.json.JSONObject;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Pili Arancibia on 9/27/19
 */
public class SectorDB {

    //Create or Update
    public final static void add(final Sector sector) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(sector);
            }
        });
        realm.close();
    }

    public final static void add(final List<Sector> sectors) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for(Sector sector: sectors) {
                    realm.copyToRealmOrUpdate(sector);
                }
            }
        });
        realm.close();
    }

    public final static void update(final JSONObject sector) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createOrUpdateObjectFromJson(Sector.class, sector);
            }
        });
        realm.close();
    }

    //Get
    public final static RealmResults<Sector> getAll(String greenhouse) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Sector> sectors = realm.where(Sector.class).equalTo("greenhouseId", greenhouse).findAll();
        return sectors;
    }

    public final static int getCount(String greenhouse) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Sector> sectors = realm.where(Sector.class).equalTo("greenhouseId", greenhouse).findAll();
        return sectors.size();
    }

    public final static Sector getForId(String id) {
        Realm realm = Realm.getDefaultInstance();
        Sector sector = realm.where(Sector.class).equalTo("_id", id).findFirst();
        return sector;
    }

}
