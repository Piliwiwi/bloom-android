package com.arech.bloom.network.req;

/**
 * Created by Pili Arancibia on 5/11/19
 */
public class LoginRequest {
    private String email;
    private String password;
    private boolean gethash;

    public LoginRequest(String email, String password, boolean gethash) {
        this.email = email;
        this.password = password;
        this.gethash = gethash;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
