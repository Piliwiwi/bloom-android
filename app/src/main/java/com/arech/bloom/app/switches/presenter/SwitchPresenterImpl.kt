package com.arech.bloom.app.switches.presenter

import com.arech.bloom.app.switches.SwitchView
import com.arech.bloom.app.switches.model.SwitchInteractorImpl
import com.arech.bloom.models.Sensor
import com.arech.bloom.models.Switch
import com.arech.bloom.models.Zone

/**
 * Created by Pili Arancibia on 1/11/20
 */

class SwitchPresenterImpl(private val switchView: SwitchView): SwitchPresenter {
    private val switchInteractor = SwitchInteractorImpl(this)

    override fun getSwitches(nodeId: String) {
        switchInteractor.getSwitches(nodeId)
    }

    override fun showSwitches(switches: List<Switch>?) {
        switchView.showSwitches(switches)
    }

    override fun getSensors(nodeId: String) {
        switchInteractor.getSensors(nodeId)
    }

    override fun showSensors(sensors: List<Sensor>?) {
        switchView.showSensors(sensors)
    }

    /* PATCH */
    override fun getSwitchesFromZone(zoneId: String) {
        switchInteractor.getSwitchesFromZone(zoneId)
    }

    override fun getSensorsFromZone(zoneId: String) {
        switchInteractor.getSensorsFromZone(zoneId)
    }

    override fun manageErrors(error: Int) {
        switchView.manageErrors(error)
    }
}