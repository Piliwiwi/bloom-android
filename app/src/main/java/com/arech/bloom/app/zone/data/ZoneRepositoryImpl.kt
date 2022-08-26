package com.arech.bloom.app.zone.data

import android.util.Log
import com.arech.bloom.R
import com.arech.bloom.app.zone.data.interfaces.ZoneRepository
import com.arech.bloom.app.zone.data.remote.model.RemoteZone
import com.arech.bloom.app.zone.data.remote.model.ZoneEditRequest
import com.arech.bloom.app.zone.presenter.interfaces.ZonePresenter
import com.arech.bloom.app.zone.presenter.mapper.PresenterZonesMapper
import com.arech.bloom.network.call.ZoneCall
import com.arech.bloom.network.callbacks.BloomCallback

class ZoneRepositoryImpl(private val zonePresenter: ZonePresenter) : ZoneRepository {

    override fun getZonesAPI(sectorId: String, mapper: PresenterZonesMapper) {
        ZoneCall.getAllFromSectorFromNetwork(sectorId, object : BloomCallback {
            override fun onSuccess(value: Any?, status: Int) {
                if (value is List<*>) {
                    val response: List<RemoteZone> = value as List<RemoteZone>
                    val presenterResponse = response.map { remoteZone -> with(mapper) { remoteZone.fromRemoteToPresenter() } }
                    zonePresenter.showZones(presenterResponse)
                }
            }

            override fun onServerError(status: Int) {
                getZonesDB(sectorId)
                zonePresenter.manageErrors(R.string.net_error)
            }

            override fun onError(throwable: Throwable) {
                throwable.printStackTrace()
            }
        })
    }

    override fun editZoneAPI(id: String, plantCount: Int?, crop: String?) {
        val request = ZoneEditRequest(plantCount, crop )
        ZoneCall.editZone(id, request, object : BloomCallback {
            override fun onSuccess(value: Any?, status: Int) {
                zonePresenter.successEditZone(id, plantCount, crop)
            }
            override fun onServerError(status: Int) {
                Log.e("SERVER ERROR", "ZONE MODIFICATION ERROR - status: " + status)
            }
            override fun onError(throwable: Throwable) {
                Log.e("ERROR", "ZONE MODIFICATION ERROR")
            }
        })
    }

    override fun getZonesDB(sectorId: String) {
        // TODO: persistence
        //zonePresenter.showZones(ZoneDB.getAll(sectorId))
    }

}