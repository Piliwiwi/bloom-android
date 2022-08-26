package com.arech.bloom.app.switches.model

import com.arech.bloom.models.Zone

/**
 * Created by Pili Arancibia on 1/11/20
 */

interface SwitchRepository {
    fun getSwitchesAPI(nodeId: String)
    fun getSwitchesDB(nodeId: String)

    fun getSensorsAPI(nodeId: String)
    fun getSensorsDB(nodeId: String)

    /* PATCH */
    fun getSwitchesFromZoneAPI(zoneId: String)
    fun getSwitchesFromZoneDB(zoneId: Zone)

    fun getSensorsFromZoneAPI(zoneId: String)
    fun getSensorsFromZoneDB(zoneId: String)
}