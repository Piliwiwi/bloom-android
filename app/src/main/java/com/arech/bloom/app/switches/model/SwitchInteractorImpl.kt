package com.arech.bloom.app.switches.model

import com.arech.bloom.app.switches.presenter.SwitchPresenter
import com.arech.bloom.core.crud.SensorDB
import com.arech.bloom.core.crud.SwitchDB
import com.arech.bloom.models.Zone

/**
 * Created by Pili Arancibia on 1/11/20
 */

class SwitchInteractorImpl(switchPresenter: SwitchPresenter): SwitchInteractor {
    private val switchRepository = SwitchRepositoryImpl(switchPresenter)

    override fun getSwitches(nodeId: String) {
        switchRepository.getSwitchesAPI(nodeId)
    }

    override fun getSensors(nodeId: String) {
        switchRepository.getSensorsAPI(nodeId)
    }

    override fun getSwitchesFromZone(zoneId: String) {
        switchRepository.getSwitchesFromZoneAPI(zoneId)
    }

    override fun getSensorsFromZone(zoneId: String) {
        switchRepository.getSensorsFromZoneAPI(zoneId)
    }
}