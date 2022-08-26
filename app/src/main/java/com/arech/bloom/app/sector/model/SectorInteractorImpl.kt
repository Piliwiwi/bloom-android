package com.arech.bloom.app.sector.model

import com.arech.bloom.app.sector.presenter.SectorPresenter
import com.arech.bloom.core.crud.SectorDB

/**
 * Created by Pili Arancibia on 9/27/19
 */

class SectorInteractorImpl(sectorPresenter: SectorPresenter): SectorInteractor {
    private val sectorRepository = SectorRepositoryImpl(sectorPresenter)

    override fun getSectors(greenhouseId: String) {
        sectorRepository.getSectorsAPI(greenhouseId)
    }

}