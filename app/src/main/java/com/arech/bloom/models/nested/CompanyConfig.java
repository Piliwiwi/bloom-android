package com.arech.bloom.models.nested;

import io.realm.RealmList;
import io.realm.RealmObject;

public class CompanyConfig extends RealmObject {
    private String users;
    private RealmList<String> services;
    private RealmList<String> customs;

    public RealmList<String> getCustoms() {
        return customs;
    }

    public RealmList<String> getServices() {
        return services;
    }

    public String getUsers() {
        return users;
    }
}
