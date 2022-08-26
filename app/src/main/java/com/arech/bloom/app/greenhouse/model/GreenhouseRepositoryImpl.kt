package com.arech.bloom.app.greenhouse.model

import com.arech.bloom.R
import com.arech.bloom.app.greenhouse.model.interfaces.GreenhouseRepository
import com.arech.bloom.app.greenhouse.presenter.interfaces.GreenhousePresenter
import com.arech.bloom.core.crud.GreenhouseDB
import com.arech.bloom.models.Greenhouse
import com.arech.bloom.network.call.GreenhouseCall
import com.arech.bloom.network.callbacks.BloomCallback
import com.arech.bloom.network.res.GreenhouseListResponse

/**
 * Created by Pili Arancibia on 8/18/19
 */

class GreenhouseRepositoryImpl(val greenhousePresenter: GreenhousePresenter): GreenhouseRepository {
    override fun getGreenhousesAPI() {
        GreenhouseCall.getAllFromNetwork(object : BloomCallback {
            override fun onSuccess(value: Any?, status: Int) {
                val response: GreenhouseListResponse = value as GreenhouseListResponse
                GreenhouseDB.add(response.data)
                greenhousePresenter.showGreenhouses(GreenhouseDB.getAll())
            }

            override fun onServerError(status: Int) {
                getGreenhousesDB()
                greenhousePresenter.manageErrors(R.string.net_error)
            }

            override fun onError(throwable: Throwable) {
                throwable.printStackTrace()
            }

        })
    }

    override fun getGreenhousesDB() {
        greenhousePresenter.showGreenhouses(GreenhouseDB.getAll())
    }

}