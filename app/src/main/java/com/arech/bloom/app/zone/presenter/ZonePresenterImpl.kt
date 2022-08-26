package com.arech.bloom.app.zone.presenter

import com.arech.bloom.app.zone.ZoneView
import com.arech.bloom.app.zone.data.ZoneInteractorImpl
import com.arech.bloom.app.zone.presenter.interfaces.ZonePresenter
import com.arech.bloom.app.zone.presenter.mapper.PresenterZonesMapper
import com.arech.bloom.app.zone.presenter.model.PresenterZone

class ZonePresenterImpl(private val zoneView: ZoneView): ZonePresenter {
    private val zoneInteractor = ZoneInteractorImpl(this)

    override fun getZones(sectorId: String, mapper: PresenterZonesMapper) {
        zoneInteractor.getZones(sectorId, mapper)
    }

    override fun editZone(id: String, plantCount: Int?, crop: String?) {
        zoneInteractor.editZone(id, plantCount, crop)
    }

    override fun successEditZone(id: String, plantCount: Int?, crop: String?) {
        zoneView.successEditZone(id, plantCount, crop)
    }

    override fun showZones(zones: List<PresenterZone>) {
        zoneView.showZones(zones)
    }

    override fun manageErrors(error: Int) {
        zoneView.manageErrors(error)
    }
}