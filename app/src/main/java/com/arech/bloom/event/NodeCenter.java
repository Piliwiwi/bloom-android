package com.arech.bloom.event;

import com.arech.bloom.models.Node;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Pili Arancibia on 9/28/19
 */
public class NodeCenter {
    public static class NodeEvent {
        public ArrayList<String> zonesId;
        public final String lastSync;

        public NodeEvent(ArrayList<String> zonesId, String lastSync) {
            this.zonesId = zonesId;
            this.lastSync = lastSync;
        }
    }
}
