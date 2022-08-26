package com.arech.bloom.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Pili Arancibia on 10/15/20
 */

public class Zone extends RealmObject {
    @PrimaryKey
    private String _id;
    private int number;
    private String name;
    private String sector;
    private boolean isActive;
    private boolean collapsed = true;

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getSector() {
        return sector;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}