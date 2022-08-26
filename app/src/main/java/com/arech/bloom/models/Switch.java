package com.arech.bloom.models;

import com.arech.bloom.models.nested.Airing;
import com.arech.bloom.models.nested.Heating;
import com.arech.bloom.models.nested.Irrigation;
import com.arech.bloom.models.nested.Light;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Pili Arancibia on 5/10/19
 */

public class Switch extends RealmObject {
    @PrimaryKey
    private String _id;
    private String mode;
    private String name;
    private boolean isActive;
    private boolean isAuto;
    private String modifiedAt; // review
    private String sensor;
    private String nodeId;
    private String greenhouse;
    private Irrigation irrigation;
    private Light light;
    private Airing airing;
    private Heating heating;
    private String sectorId;
    private int bed;
    /* PATCH */
    private String zone;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isAuto() {
        return isAuto;
    }

    public void setAuto(boolean auto) {
        isAuto = auto;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public Irrigation getIrrigation() {
        return irrigation;
    }

    public void setIrrigation(Irrigation irrigation) {
        this.irrigation = irrigation;
    }

    public Light getLight() {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }

    public Airing getAiring() {
        return airing;
    }

    public void setAiring(Airing airing) {
        this.airing = airing;
    }

    public Heating getHeating() {
        return heating;
    }

    public void setHeating(Heating heating) {
        this.heating = heating;
    }

    public String getSectorId() {
        return sectorId;
    }

    public void setSectorId(String sectorId) {
        this.sectorId = sectorId;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZone() {
        return zone;
    }

    public int getBed() {
        return bed;
    }
}
