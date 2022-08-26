package com.arech.bloom.network.res

import com.arech.bloom.models.Greenhouse

/**
 * Created by Pili Arancibia on 3/16/21.
 */
data class GreenhouseListResponse (
        val data: List<Greenhouse>?,
        val message: String?
)