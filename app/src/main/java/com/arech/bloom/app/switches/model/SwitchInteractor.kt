package com.arech.bloom.app.switches.model

import com.arech.bloom.models.Zone

/**
 * Created by Pili Arancibia on 1/11/20
 */

interface SwitchInteractor {
    fun getSwitches(nodeId: String)
    fun getSensors(nodeId: String)

    fun getSwitchesFromZone(zoneId: String)
    fun getSensorsFromZone(zoneId: String)
}