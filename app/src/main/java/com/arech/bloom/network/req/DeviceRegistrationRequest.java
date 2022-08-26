package com.arech.bloom.network.req;

public class DeviceRegistrationRequest {
    private String token;

    public DeviceRegistrationRequest(String _token) {
        this.token = _token;
    }

    public String getToken() {
        return this.token;
    }
}
