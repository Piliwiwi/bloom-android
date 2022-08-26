package com.arech.bloom.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Pili Arancibia on 5/10/19
 */

public class Greenhouse extends RealmObject {
    @PrimaryKey
    private String _id;
    private String name;
    private int number;
    private int vitality;
    private double lat;
    private double lng;
    private String fieldId;
    private String manager;
    private float surface;
    private int plantCount;
    private int sectorCount;
    private int nodeCount;
    private RealmList<String> users;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) { this._id = _id; }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) { this.number = number; }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) { this.fieldId = fieldId; }

    public RealmList<String> getUserId() {
        return users;
    }

    public int getSectorCount() { return sectorCount; }

    public void setSectorCount(int sectorCount) { this.sectorCount = sectorCount; }

    public int getPlantCount() { return plantCount; }

    public void setPlantCount(int plantCount) { this.plantCount = plantCount; }

    public float getSurface() { return surface; }

    public void setSurface(float surface) { this.surface = surface; }

    public String getManager() { return manager; }

    public void setManager(String manager) { this.manager = manager; }

    public int getNodeCount() { return nodeCount; }

    public void setNodeCount(int nodeCount) { this.nodeCount = nodeCount; }

    public void setUsers(RealmList<String> users) { this.users = users; }

    public RealmList<String> getUsers() { return this.users; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVitality() {
        return vitality;
    }

    public void setVitality(int vitality) {
        this.vitality = vitality;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
