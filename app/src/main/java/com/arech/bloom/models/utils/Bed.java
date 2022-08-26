package com.arech.bloom.models.utils;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Bed extends RealmObject {
    @PrimaryKey
    public String _id;
    private String zone;
    private String sectorId;
    private boolean collapsed = true;

    public Bed() {
        this._id = "bed";
        this.zone = "";
        this.sectorId = "";
    }

    public Bed(String _id, String zone, String sectorId) {
        this._id = _id;
        this.zone = zone;
        this.sectorId = sectorId;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public String get_id() {
        return _id;
    }

    public String getSectorId() {
        return sectorId;
    }

    public String getZone() {
        return zone;
    }
}
