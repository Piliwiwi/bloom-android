package com.arech.bloom.core.crud;

import com.arech.bloom.models.Node;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Pili Arancibia on 9/28/19
 */
public class NodeDB {
    //Create or Update
    public static void add(final Node node) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(node);
            }
        });
        realm.close();
    }

    public static void add(final List<Node> nodes) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for(Node node: nodes) {
                    realm.copyToRealmOrUpdate(node);
                }
            }
        });
        realm.close();
    }

    public static void update(final JSONObject node) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.createOrUpdateObjectFromJson(Node.class, node);
            }
        });
        realm.close();
    }

    public static void setCollapse(final String id, final boolean collapse) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Node node = realm.where(Node.class).equalTo("_id", id).findFirst();
                assert node != null;
                node.setCollapsed(collapse);
            }
        });
        realm.close();
    }

    //Get
    public final static RealmResults<Node> getAll(String sector) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Node> nodes = realm.where(Node.class).equalTo("sectorId", sector).findAll();
        realm.close();
        return nodes;
    }

    public final static int getCount(String greenhouseId) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Node> nodes = realm.where(Node.class).equalTo("greenhouse", greenhouseId).findAll();
        realm.close();
        return nodes.size();
    }

    public final static Node getForId(String id) {
        Realm realm = Realm.getDefaultInstance();
        Node node = realm.where(Node.class).equalTo("_id", id).findFirst();
        realm.close();
        return node;
    }

    public final static RealmResults<Node> getForIds(List<String> ids) {
        Realm realm = Realm.getDefaultInstance();
        String[] idsQuery = new String[ids.size()];
        for (int i=0; i<ids.size(); i++) idsQuery[i] = ids.get(i);
        RealmResults<Node> nodes = realm.where(Node.class).in("_id", idsQuery).findAll();
        realm.close();
        return nodes;
    }
}
