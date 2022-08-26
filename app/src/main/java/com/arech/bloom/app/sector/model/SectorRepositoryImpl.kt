package com.arech.bloom.app.sector.model

import com.arech.bloom.R
import com.arech.bloom.app.sector.presenter.SectorPresenter
import com.arech.bloom.core.crud.SectorDB
import com.arech.bloom.models.Sector
import com.arech.bloom.network.call.SectorCall
import com.arech.bloom.network.callbacks.BloomCallback
import com.arech.bloom.network.res.BloomServerListResponse

/**
 * Created by Pili Arancibia on 9/27/19
 */

class SectorRepositoryImpl(val sectorPresenter: SectorPresenter): SectorRepository {
    override fun getSectorsAPI(greenhouseId: String) {
        SectorCall.getAllInFromNetwork(greenhouseId, object : BloomCallback {
            override fun onSuccess(value: Any?, status: Int) {
                val response: BloomServerListResponse = value as BloomServerListResponse
                val data = response.data as List<Sector>
                SectorDB.add(data)
                sectorPresenter.showSectors(data)
            }

            override fun onServerError(status: Int) {
                getSectorsDB(greenhouseId)
                sectorPresenter.manageErrors(R.string.net_error)
            }

            override fun onError(throwable: Throwable) {
                throwable.printStackTrace()
            }
        })
    }

    override fun getSectorsDB(greenhouseId: String) {
        sectorPresenter.showSectors(SectorDB.getAll(greenhouseId))
    }
}