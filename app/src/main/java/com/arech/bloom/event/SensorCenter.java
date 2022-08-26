package com.arech.bloom.event;

import com.arech.bloom.app.zone.presenter.model.AttrsEventSensor;
import com.arech.bloom.models.Sensor;

/**
 * Created by Pili Arancibia on 9/28/19
 */
public class SensorCenter {
    public static class SensorEvent {
        public final AttrsEventSensor event;

        public SensorEvent(AttrsEventSensor event) {
            this.event = event;
        }
    }
}
