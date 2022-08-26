package com.arech.bloom.models.nested;

import io.realm.RealmObject;

/**
 * Created by Pili Arancibia on 5/10/19
 */
public class Irrigation extends RealmObject {
    private boolean isFinished;
    private boolean isWatering;
    private int wateringTime;
    private String wateringHour;
    private int tapeMeters;
    private int frecuency;

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public boolean isWatering() {
        return isWatering;
    }

    public void setWatering(boolean watering) {
        isWatering = watering;
    }

    public int getWateringTime() {
        return wateringTime;
    }

    public void setWateringTime(int wateringTime) {
        this.wateringTime = wateringTime;
    }

    public String getWateringHour() {
        return wateringHour;
    }

    public void setWateringHour(String wateringHour) {
        this.wateringHour = wateringHour;
    }

    public int getTapeMeters() {
        return tapeMeters;
    }

    public void setTapeMeters(int tapeMeters) {
        this.tapeMeters = tapeMeters;
    }

    public int getFrecuency() {
        return frecuency;
    }

    public void setFrecuency(int frecuency) {
        this.frecuency = frecuency;
    }
}
