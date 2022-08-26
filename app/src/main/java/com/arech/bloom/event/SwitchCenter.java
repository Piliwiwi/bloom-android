package com.arech.bloom.event;

/**
 * Created by Pili Arancibia on 1/11/20
 */

public class SwitchCenter {
    public static class SwitchEvent {
        public final String id;

        public SwitchEvent(String id) {
            this.id = id;
        }
    }
}