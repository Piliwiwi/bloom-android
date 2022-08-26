package com.arech.bloom.models.nested;

import io.realm.RealmObject;

/**
 * Created by Pili Arancibia on 5/10/19
 */
public class Light extends RealmObject {
    private String type;
    private int timeOn;
    private String hourOn;
    private int consumption;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTimeOn() {
        return timeOn;
    }

    public void setTimeOn(int timeOn) {
        this.timeOn = timeOn;
    }

    public String getHourOn() {
        return hourOn;
    }

    public void setHourOn(String hourOn) {
        this.hourOn = hourOn;
    }

    public int getConsumption() {
        return consumption;
    }

    public void setConsumption(int consumption) {
        this.consumption = consumption;
    }
}
