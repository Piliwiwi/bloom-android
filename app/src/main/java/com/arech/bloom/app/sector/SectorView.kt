package com.arech.bloom.app.sector

import com.arech.bloom.models.Sector

/**
 * Created by Pili Arancibia on 9/27/19
 */

interface SectorView {
    fun getSectors(greenhouseId: String?)
    fun showSectors(sectors: List<Sector>?)
    fun manageErrors(error: Int)
}