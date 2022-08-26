package com.arech.bloom.app.zone.data

import com.arech.bloom.app.zone.data.interfaces.ZoneInteractor
import com.arech.bloom.app.zone.presenter.interfaces.ZonePresenter
import com.arech.bloom.app.zone.presenter.mapper.PresenterZonesMapper

class ZoneInteractorImpl(zonePresenter: ZonePresenter): ZoneInteractor {
    private val zoneRepository = ZoneRepositoryImpl(zonePresenter)

    override fun getZones(sectorId: String, mapper: PresenterZonesMapper) {
        zoneRepository.getZonesAPI(sectorId, mapper)
    }

    override fun editZone(id: String, plantCount: Int?, crop: String?) {
        zoneRepository.editZoneAPI(id, plantCount, crop)
    }
}