package com.arech.bloom.app.zone.presenter.interfaces

import com.arech.bloom.app.zone.presenter.mapper.PresenterZonesMapper
import com.arech.bloom.app.zone.presenter.model.PresenterZone

interface ZonePresenter {
    fun getZones(sectorId: String, mapper: PresenterZonesMapper)
    fun editZone(id: String, plantCount: Int?, crop: String?)
    fun successEditZone(id: String, plantCount: Int?, crop: String?)
    fun showZones(zones: List<PresenterZone>)
    fun manageErrors(error: Int)
}