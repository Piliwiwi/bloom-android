package com.arech.bloom.app.switches.presenter

import com.arech.bloom.models.Sensor
import com.arech.bloom.models.Switch
import com.arech.bloom.models.Zone

/**
 * Created by Pili Arancibia on 1/11/20
 */

interface SwitchPresenter {
    fun getSwitches(nodeId: String)
    fun showSwitches(switches: List<Switch>?)
    fun getSensors(nodeId: String)
    fun showSensors(sensors: List<Sensor>?)
    fun manageErrors(error: Int)

    /* PATCH */

    fun getSwitchesFromZone(zoneId: String)
    fun getSensorsFromZone(zoneId: String)
}