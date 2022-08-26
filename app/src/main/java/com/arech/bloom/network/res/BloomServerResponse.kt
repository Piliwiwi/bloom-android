package com.arech.bloom.network.res

import io.realm.RealmObject
/**
 * Created by Pili Arancibia on 3/15/21
 */
data class BloomServerResponse (
        val data: RealmObject?,
        val message: String?
)