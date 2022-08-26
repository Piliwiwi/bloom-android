package com.arech.bloom.app.zone.data.remote.model

/**
 * Created by Pili Arancibia on 4/24/21.
 */

data class RemoteZone(
        val id: String?,
        val title: String?,
        val subtitle: String?,
        val isActive: Boolean?,
        val lastSync: String?,
        val crop: String?,
        val plantsLabel: String?,
        val plantCount: Int?,
        val sensors: List<RemoteZoneSensor>?
)