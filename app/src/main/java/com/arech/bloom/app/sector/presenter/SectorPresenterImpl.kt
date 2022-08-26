package com.arech.bloom.app.sector.presenter

import com.arech.bloom.app.sector.SectorView
import com.arech.bloom.app.sector.model.SectorInteractorImpl
import com.arech.bloom.models.Sector

/**
 * Created by Pili Arancibia on 9/27/19
 */

class SectorPresenterImpl(private val sectorView: SectorView): SectorPresenter {
    private val sectorInteractor = SectorInteractorImpl(this)

    override fun getSectors(greenhouseId: String) {
        sectorInteractor.getSectors(greenhouseId)
    }

    override fun showSectors(sectors: List<Sector>?) {
        sectorView.showSectors(sectors)
    }

    override fun manageErrors(error: Int) {
        sectorView.manageErrors(error)
    }
}