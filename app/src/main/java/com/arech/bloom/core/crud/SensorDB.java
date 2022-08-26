package com.arech.bloom.core.crud;

import com.arech.bloom.models.Node;
import com.arech.bloom.models.Sensor;
import com.arech.bloom.models.Zone;
import org.json.JSONObject;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Pili Arancibia on 9/28/19
 */
public class SensorDB {
    //Create or Update
    public final static void add(final Sensor sensor) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(sensor);
            }
        });
        realm.close();
    }

    public final static void update(final JSONObject sensor) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createOrUpdateObjectFromJson(Sensor.class, sensor);
            }
        });
        realm.close();
    }

    public final static void add(final List<Sensor> sensors) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for(Sensor sensor: sensors) {
                    realm.copyToRealmOrUpdate(sensor);
                }
            }
        });
        realm.close();
    }

    //Get
    public final static RealmResults<Sensor> getAllFromNode(String node) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Sensor> sensors = realm.where(Sensor.class).equalTo("nodeId", node).findAll();
        return sensors;
    }

    public final static RealmResults<Sensor> getAll() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Sensor> sensors = realm.where(Sensor.class).findAll();
        return sensors;
    }

    public final static RealmResults<Sensor> getAllFromSector(String sector) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Node> nodes = realm.where(Node.class).equalTo("sectorId", sector).findAll();
        String[] nodeIds = new String[nodes.size()];
        for (int i=0; i < nodes.size(); i++) {
            nodeIds[i] = nodes.get(i).get_id();
        }
        RealmResults<Sensor> sensors = realm.where(Sensor.class).in("nodeId", nodeIds).findAll();
        return sensors;
    }

    public final static RealmResults<Sensor> getAllFromZone(Zone zone) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Node> nodes = realm.where(Node.class).equalTo("sectorId", zone.getSector()).findAll();
        String[] nodeIds = new String[nodes.size()];
        for (int i=0; i < nodes.size(); i++) {
            nodeIds[i] = nodes.get(i).get_id();
        }
        RealmResults<Sensor> sensors = realm.where(Sensor.class).in("nodeId", nodeIds).and().equalTo("bed", zone.getNumber()).findAll();
        return sensors;
    }

    public final static RealmResults<Sensor> getAllFromZoneId(String zoneId) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Sensor> sensors = realm.where(Sensor.class).equalTo("zoneId", zoneId).findAll();
        return sensors;
    }

    public final static Sensor getById(String id) {
        Realm realm = Realm.getDefaultInstance();
        Sensor sensor = realm.where(Sensor.class).equalTo("_id", id).findFirst();
        return sensor;
    }

    public final static int getCount() {
        Realm realm = Realm.getDefaultInstance();
        int sensors = realm.where(Sensor.class).findAll().size();
        return sensors;
    }
}
