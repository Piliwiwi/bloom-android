package com.arech.bloom.app.zone.data.interfaces

import com.arech.bloom.app.zone.presenter.mapper.PresenterZonesMapper

interface ZoneRepository {
    fun getZonesAPI(sectorId: String, mapper: PresenterZonesMapper)
    fun editZoneAPI(id: String, plantCount: Int?, crop: String?)
    fun getZonesDB(sectorId: String)
}