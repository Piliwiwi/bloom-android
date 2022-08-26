package com.arech.bloom.app.greenhouse.presenter

import com.arech.bloom.app.greenhouse.GreenhouseView
import com.arech.bloom.app.greenhouse.model.GreenhouseInteractorImpl
import com.arech.bloom.app.greenhouse.presenter.interfaces.GreenhousePresenter
import com.arech.bloom.models.Greenhouse

/**
 * Created by Pili Arancibia on 8/18/19
 */

class GreenhousePresenterImpl(private val greenhouseView: GreenhouseView): GreenhousePresenter {
    private val greenhouseInteractor = GreenhouseInteractorImpl(this)

    override fun getGreenhouses() {
        greenhouseInteractor.getGreenhouses()
    }

    override fun showGreenhouses(greenhouses: List<Greenhouse>?) {
        greenhouseView.showGreenhouses(greenhouses)
    }

    override fun manageErrors(error: Int) {
        greenhouseView.manageErros(error)
    }
}