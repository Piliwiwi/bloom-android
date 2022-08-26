package com.arech.bloom.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Pili Arancibia on 5/10/19
 */

public class Sensor extends RealmObject {
    @PrimaryKey
    private String _id;
    private String type;
    private String name;
    private String model;
    private double data;
    private double max;
    private double min;
    private double permittedMax;
    private double permittedMin;
    private double optimum;
    private String modifiedAt; //review
    private String dateMax; //review
    private String dateMin; //review
    private String nodeId;
    private String greenhouse;
    private String stat;
    private String unit;
    private int bed;
    /* PATCH */
    private String zone;
    private String zoneId;
    private String medium;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getData() {
        return data;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getDateMax() {
        return dateMax;
    }

    public void setDateMax(String dateMax) {
        this.dateMax = dateMax;
    }

    public String getDateMin() {
        return dateMin;
    }

    public void setDateMin(String dateMin) {
        this.dateMin = dateMin;
    }

    public double getOptimum() {
        return optimum;
    }

    public double getPermittedMax() {
        return permittedMax;
    }

    public double getPermittedMin() {
        return permittedMin;
    }

    public void setData(double data) {
        this.data = data;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public String getStat() {
        return stat;
    }

    public String getUnit() {
        return unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getZone() {
        return zone;
    }

    public int getBed() {
        return bed;
    }

    public String getZoneId() {
        return zoneId;
    }

    public String getMedium() {
        return medium;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
