package com.arech.bloom.app.zone.data.remote.model

/**
 * Created by Pili Arancibia on 4/24/21.
 */

data class RemoteZoneSensor(
        val id: String?,
        val type: String?,
        val formattedData: String?,
        val formattedPermittedMax: String?,
        val formattedPermittedMin: String?,
        val formattedOptimum: String?,
        val medium: String?,
        val icon: String?,
        val stat: String?
)