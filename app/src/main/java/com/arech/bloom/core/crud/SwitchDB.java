package com.arech.bloom.core.crud;

import com.arech.bloom.models.Node;
import com.arech.bloom.models.Sensor;
import com.arech.bloom.models.Switch;
import com.arech.bloom.models.Zone;
import com.arech.bloom.models.utils.Bed;

import org.json.JSONObject;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class SwitchDB {
    public final static void add(final Switch maSwitch) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(maSwitch);
            }
        });
        realm.close();
    }

    public final static void update(final JSONObject maSwitch) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createOrUpdateObjectFromJson(Switch.class, maSwitch);
            }
        });
        realm.close();
    }

    public final static void add(final List<Switch> switches) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for(Switch maSwitch: switches) {
                    realm.copyToRealmOrUpdate(maSwitch);
                }
            }
        });
        realm.close();
    }

    public static void setOn(final String id, final boolean isOn) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Switch maSwitch = realm.where(Switch.class).equalTo("_id", id).findFirst();
                assert maSwitch != null;
                maSwitch.setActive(isOn);
            }
        });
        realm.close();
    }

    public static void setAuto(final String id, final boolean isAuto) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Switch maSwitch = realm.where(Switch.class).equalTo("_id", id).findFirst();
                assert maSwitch != null;
                maSwitch.setAuto(isAuto);
            }
        });
        realm.close();
    }

    //Get
    public final static RealmResults<Switch> getAll(String node) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Switch> switches = realm.where(Switch.class).equalTo("nodeId", node).findAll();
        return switches;
    }

    public final static RealmResults<Switch> getAllFromSector(String sector) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Node> nodes = realm.where(Node.class).equalTo("sectorId", sector).findAll();
        String[] nodeIds = new String[nodes.size()];
        for (int i=0; i < nodes.size(); i++) {
            nodeIds[i] = nodes.get(i).get_id();
        }
        RealmResults<Switch> switches = realm.where(Switch.class).in("nodeId", nodeIds).findAll();
        return switches;
    }

    public final static RealmResults<Switch> getAllFromZone(Zone zone) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Node> nodes = realm.where(Node.class).equalTo("sectorId", zone.getSector()).findAll();
        String[] nodeIds = new String[nodes.size()];
        for (int i=0; i < nodes.size(); i++) {
            nodeIds[i] = nodes.get(i).get_id();
        }
        RealmResults<Sensor> sensors = realm.where(Sensor.class).in("nodeId", nodeIds).and().equalTo("bed", zone.getNumber()).findAll();
        String[] sensorIds = new String[sensors.size()];
        for (int i=0; i < sensors.size(); i++) {
            sensorIds[i] = sensors.get(i).get_id();
        }

        RealmQuery<Switch> allSwitchesInSector = realm.where(Switch.class).in("nodeId", nodeIds);
        RealmResults<Switch> switches = allSwitchesInSector.in("sensor", sensorIds).findAll();
        return switches;
    }

    public static Switch getForId(String id) {
        Realm realm = Realm.getDefaultInstance();
        Switch maSwitch = realm.where(Switch.class).equalTo("_id", id).findFirst();
        return maSwitch;
    }

    public static int getCount() {
        Realm realm = Realm.getDefaultInstance();
        int switches = realm.where(Switch.class).findAll().size();
        return switches;
    }
}
