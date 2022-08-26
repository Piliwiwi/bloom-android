package com.arech.bloom.models;

import io.realm.RealmObject;

/**
 * Created by Pili Arancibia on 3/15/21
 */

public class SensorsInfo extends RealmObject {
    private float highl;
    private float criticMaxCount;
    private float criticMinCount;
    private float warningMaxCount;
    private float warningMinCount;
    private float inactiveCount;
    private float greenCount;

    public float getHighl() {
        return highl;
    }

    public float getCriticMaxCount() {
        return criticMaxCount;
    }

    public float getCriticMinCount() {
        return criticMinCount;
    }

    public float getWarningMaxCount() {
        return warningMaxCount;
    }

    public float getWarningMinCount() {
        return warningMinCount;
    }

    public float getInactiveCount() {
        return inactiveCount;
    }

    public float getGreenCount() {
        return greenCount;
    }
}
