package com.arech.bloom.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Pili Arancibia on 5/10/19
 */

public class Field extends RealmObject {
    @PrimaryKey
    private String _id;
    private int number;
    private double lat;
    private double lng;
    private int vitality;
    private RealmList<String> users;

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

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
