package com.arech.bloom.event;

/**
 * Created by Pili Arancibia on 9/28/19
 */
public class SectorCenter {
    public static class SectorEvent {
        public final boolean reloadUI;
        public final boolean reloadNetwork;

        public SectorEvent(boolean reloadUI, boolean reloadNetwork) {
            this.reloadUI = reloadUI;
            this.reloadNetwork = reloadNetwork;
        }
    }
}
