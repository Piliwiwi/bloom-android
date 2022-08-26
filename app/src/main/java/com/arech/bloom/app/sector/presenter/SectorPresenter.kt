package com.arech.bloom.app.sector.presenter

import com.arech.bloom.models.Sector

/**
 * Created by Pili Arancibia on 9/27/19
 */

interface SectorPresenter {
    fun getSectors(greenhouseId: String)
    fun showSectors(sectors: List<Sector>?)
    fun manageErrors(error: Int)
}