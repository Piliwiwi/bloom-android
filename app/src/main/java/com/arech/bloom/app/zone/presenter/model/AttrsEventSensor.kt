package com.arech.bloom.app.zone.presenter.model

/**
 * Created by Pili Arancibia on 5/4/21.
 */

data class AttrsEventSensor(
        val id: String,
        val zoneId: String?,
        val formattedData: String?,
        val formattedStat: String?
)