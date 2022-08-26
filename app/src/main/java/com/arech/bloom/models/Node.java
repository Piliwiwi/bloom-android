package com.arech.bloom.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Pili Arancibia on 5/10/19
 */

public class Node extends RealmObject {
    @PrimaryKey
    private String _id;
    private int number;
    private float vitality;
    private float charge;
    private String crop;
    private String sectorId;
    private Date lastSync;
    private long ds;
    private String greenhouse;

    //Local
    private boolean collapsed = true;

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

    public float getCharge() {
        return charge;
    }

    public String getSectorId() {
        return sectorId;
    }

    public void setSectorId(String sectorId) {
        this.sectorId = sectorId;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public Date getLastSync() {
        return lastSync;
    }

    public void setLastSync(Date lastSync) {
        this.lastSync = lastSync;
    }

    public long getDs() {
        return ds;
    }

    public void setDs(long ds) {
        this.ds = ds;
    }
}
