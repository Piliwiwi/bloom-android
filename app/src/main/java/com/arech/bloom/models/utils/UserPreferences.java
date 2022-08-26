package com.arech.bloom.models.utils;

import com.arech.bloom.config.Resources;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Pili Arancibia on 5/11/19
 */
public class UserPreferences extends RealmObject {
    @PrimaryKey
    private String identifier;
    private String userId;
    private String token;

    //LOCAL
    private boolean hasBedView = false;

    public UserPreferences() {
        this.identifier = Resources.CURRENT_USER_IDENTIFIER;
        this.userId = "";
        this.token = "";
    }

    public UserPreferences(String identifier, String userId, String token) {
        this.identifier = identifier;
        this.userId = userId;
        this.token = token;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public boolean isHasBedView() {
        return hasBedView;
    }

    public void setHasBedView(boolean hasBedView) {
        this.hasBedView = hasBedView;
    }
}
