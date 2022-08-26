package com.arech.bloom.network.req;

public class ZoneModificationRequest {
    private boolean isActive;

    public ZoneModificationRequest(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
