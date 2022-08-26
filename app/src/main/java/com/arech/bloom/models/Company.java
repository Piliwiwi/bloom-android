package com.arech.bloom.models;

import com.arech.bloom.models.nested.CompanyConfig;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Company extends RealmObject {
    @PrimaryKey
    private String _id;
    private String name;
    private String manager;
    private Date contractExpirationDate;
    private CompanyConfig config;

    public CompanyConfig getConfig() {
        return config;
    }

    public String getName() {
        return name;
    }
}

