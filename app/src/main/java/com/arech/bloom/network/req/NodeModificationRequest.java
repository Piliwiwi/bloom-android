package com.arech.bloom.network.req;

public class NodeModificationRequest {
    private long ds;

    public NodeModificationRequest(long ds) {
        this.ds = ds;
    }

    public long getDs() {
        return ds;
    }

    public void setDs(long ds) {
        this.ds = ds;
    }
}
