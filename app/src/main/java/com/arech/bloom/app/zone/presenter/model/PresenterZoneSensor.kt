package com.arech.bloom.app.zone.presenter.model

/**
 * Created by Pili Arancibia on 4/24/21.
 */

data class PresenterZoneSensor(
        val id: String,
        val type: String,
        var formattedData: String,
        val formattedPermittedMax: String,
        val formattedPermittedMin: String,
        val formattedOptimum: String,
        val medium: String,
        val icon: String,
        var stat: String
)