package com.arech.bloom.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Pili Arancibia on 5/10/19
 */

public class Sector extends RealmObject {
    @PrimaryKey
    private String _id;
    private int number;
    private int vitality;
    private int plantCount;
    private String crop;
    private String greenhouseId;
    private int zones;
    private SensorsInfo sensorsInfo;

    public String get_id() {
        return _id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public String getCrop() {
        return crop;
    }
    public void setCrop(String crop) {
        this.crop = crop;
    }
    public String getGreenhouseId() {
        return greenhouseId;
    }
    public void setGreenhouseId(String greenhouseId) {
        this.greenhouseId = greenhouseId;
    }
    public int getPlantCount() { return plantCount; }
    public void setPlantCount(int plantCount) { this.plantCount = plantCount; }
    public int getVitality() { return vitality; }
    public void setVitality(int vitality) { this.vitality = vitality; }

    public int getZones() {
        return zones;
    }

    public SensorsInfo getSensorsInfo() {
        return sensorsInfo;
    }
}
