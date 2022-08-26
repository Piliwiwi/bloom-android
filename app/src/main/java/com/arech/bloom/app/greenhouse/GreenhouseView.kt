package com.arech.bloom.app.greenhouse

import com.arech.bloom.models.Greenhouse

/**
 * Created by Pili Arancibia on 8/18/19
 */

interface GreenhouseView {
    fun getGreenhouses()
    fun showGreenhouses(greenhouses: List<Greenhouse>?)
    fun manageErros(error: Int)
}