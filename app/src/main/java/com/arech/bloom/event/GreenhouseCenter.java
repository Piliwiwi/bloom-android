package com.arech.bloom.event;

/**
 * Created by Pili Arancibia on 9/28/19
 */
public class GreenhouseCenter {
    public static class GreenhouseEvent {
        public final boolean reloadUI;
        public final boolean reloadNetwork;

        public GreenhouseEvent(boolean reloadUI, boolean reloadNetwork) {
            this.reloadUI = reloadUI;
            this.reloadNetwork = reloadNetwork;
        }
    }
}
