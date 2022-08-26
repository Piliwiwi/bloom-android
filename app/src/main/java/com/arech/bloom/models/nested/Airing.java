package com.arech.bloom.models.nested;

import io.realm.RealmObject;

/**
 * Created by Pili Arancibia on 5/10/19
 */
public class Airing extends RealmObject {
    private int slogan;
    private int hysteresis;
    private int cooldown;

    public int getSlogan() {
        return slogan;
    }

    public void setSlogan(int slogan) {
        this.slogan = slogan;
    }

    public int getHysteresis() {
        return hysteresis;
    }

    public void setHysteresis(int hysteresis) {
        this.hysteresis = hysteresis;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
}
