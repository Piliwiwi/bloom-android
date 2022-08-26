package com.arech.bloom.app.zone.data.interfaces

import com.arech.bloom.app.zone.presenter.mapper.PresenterZonesMapper

interface ZoneInteractor {
    fun getZones(sectorId: String, mapper: PresenterZonesMapper)
    fun editZone(id: String, plantCount: Int?, crop: String?)
}