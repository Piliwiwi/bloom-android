package com.arech.bloom.network.res

import com.arech.bloom.models.Sector
import io.realm.RealmObject
/**
 * Created by Pili Arancibia on 3/15/21
 */
data class BloomServerListResponse (
        val data: List<Sector>?,
        val message: String?
)