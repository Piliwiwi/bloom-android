package com.arech.bloom.network.req;

public class SwitchModificationRequest {
    private boolean isActive;
    private boolean isAuto;

    public SwitchModificationRequest(boolean isActive, boolean isAuto) {
        this.isActive = isActive;
        this.isAuto = isAuto;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isAuto() {
        return isAuto;
    }

    public void setAuto(boolean auto) {
        isAuto = auto;
    }
}
