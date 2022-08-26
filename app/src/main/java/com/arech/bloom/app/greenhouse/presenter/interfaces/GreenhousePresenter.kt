package com.arech.bloom.app.greenhouse.presenter.interfaces

import com.arech.bloom.models.Greenhouse

/**
 * Created by Pili Arancibia on 8/18/19
 */

interface GreenhousePresenter {
    fun getGreenhouses()
    fun showGreenhouses(greenhouses: List<Greenhouse>?)
    fun manageErrors(error: Int)
}