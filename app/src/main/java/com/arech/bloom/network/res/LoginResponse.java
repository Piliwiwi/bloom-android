package com.arech.bloom.network.res;

import com.arech.bloom.models.User;

/**
 * Created by Pili Arancibia on 5/11/19
 */
public class LoginResponse {
    private User user;
    private String token;

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}
