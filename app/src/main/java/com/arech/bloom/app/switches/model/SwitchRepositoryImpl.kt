package com.arech.bloom.app.switches.model

import com.arech.bloom.R
import com.arech.bloom.app.switches.presenter.SwitchPresenter
import com.arech.bloom.core.crud.SensorDB
import com.arech.bloom.core.crud.SwitchDB
import com.arech.bloom.models.Sensor
import com.arech.bloom.models.Switch
import com.arech.bloom.models.Zone
import com.arech.bloom.network.call.SensorCall
import com.arech.bloom.network.call.SwitchCall
import com.arech.bloom.network.callbacks.BloomCallback

/**
 * Created by Pili Arancibia on 1/11/20
 */

class SwitchRepositoryImpl(private val switchPresenter: SwitchPresenter): SwitchRepository {

    override fun getSwitchesAPI(nodeId: String) {
        SwitchCall.getAllInFromNetwork(nodeId, object : BloomCallback {
            override fun onSuccess(value: Any?, status: Int) {
                if (value is List<*>) {
                    val response: List<Switch> = value as List<Switch>
                    SwitchDB.add(response)
                    switchPresenter.showSwitches(SwitchDB.getAll(nodeId))
                }
            }

            override fun onServerError(status: Int) {
                getSwitchesDB(nodeId)
                switchPresenter.manageErrors(R.string.net_error)
            }

            override fun onError(throwable: Throwable) {
                throwable.printStackTrace()
            }
        })
    }

    override fun getSwitchesDB(nodeId: String) {
        switchPresenter.showSwitches(SwitchDB.getAll(nodeId))
    }

    override fun getSensorsAPI(nodeId: String) {
        SensorCall.getAllInFromNetwork(nodeId, object : BloomCallback {
            override fun onSuccess(value: Any?, status: Int) {
                if (value is List<*>) {
                    val response: List<Sensor> = value as List<Sensor>
                    SensorDB.add(response)
                    switchPresenter.showSensors(SensorDB.getAllFromNode(nodeId))
                }
            }

            override fun onServerError(status: Int) {
                getSensorsDB(nodeId)
                switchPresenter.manageErrors(R.string.net_error)
            }

            override fun onError(throwable: Throwable) {
                throwable.printStackTrace()
            }
        })
    }

    override fun getSensorsDB(nodeId: String) {
        switchPresenter.showSensors(SensorDB.getAllFromNode(nodeId))
    }

    /* PATCH */
    override fun getSwitchesFromZoneAPI(zoneId: String) {
        SwitchCall.getAllSwitchesInZoneFromNetwork(zoneId, object : BloomCallback {
            override fun onSuccess(value: Any?, status: Int) {
                if (value is List<*>) {
                    val response: List<Switch> = value as List<Switch>
                    SwitchDB.add(response)
                    switchPresenter.showSwitches(response)
                }
            }

            override fun onServerError(status: Int) {
                //getSwitchesFromZoneDB(zone)
                switchPresenter.manageErrors(R.string.net_error)
            }

            override fun onError(throwable: Throwable) {
                throwable.printStackTrace()
            }
        })
    }

    override fun getSwitchesFromZoneDB(zone: Zone) {
        switchPresenter.showSwitches(SwitchDB.getAllFromZone(zone))
    }

    override fun getSensorsFromZoneAPI(zoneId: String) {
        SensorCall.getAllSensorsInZoneFromNetwork(zoneId, object : BloomCallback {
            override fun onSuccess(value: Any?, status: Int) {
                if (value is List<*>) {
                    val response: List<Sensor> = value as List<Sensor>
                    SensorDB.add(response)
                    switchPresenter.showSensors(response)
                }
            }

            override fun onServerError(status: Int) {
                //getSensorsFromZoneDB(zone)
                switchPresenter.manageErrors(R.string.net_error)
            }

            override fun onError(throwable: Throwable) {
                throwable.printStackTrace()
            }
        })
    }

    override fun getSensorsFromZoneDB(zoneId: String) {
        //switchPresenter.showSensors(SensorDB.getAllFromZoneId(zone._id))
    }
}