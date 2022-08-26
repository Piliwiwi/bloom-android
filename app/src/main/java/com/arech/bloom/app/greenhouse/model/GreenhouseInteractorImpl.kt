package com.arech.bloom.app.greenhouse.model

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.arech.bloom.app.greenhouse.model.interfaces.GreenhouseInteractor
import com.arech.bloom.app.greenhouse.presenter.interfaces.GreenhousePresenter
import com.arech.bloom.core.crud.GreenhouseDB
import com.arech.bloom.utils.ObservableInternetConnection

/**
 * Created by Pili Arancibia on 8/18/19
 */

class GreenhouseInteractorImpl(greenhousePresenter: GreenhousePresenter): GreenhouseInteractor {
    private val greenhouseRepository = GreenhouseRepositoryImpl(greenhousePresenter)

    override fun getGreenhouses() {
        greenhouseRepository.getGreenhousesAPI()
    }
}