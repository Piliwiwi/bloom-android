package com.arech.bloom.app.zone.presenter.model

/**
 * Created by Pili Arancibia on 4/24/21.
 */

data class PresenterZone(
        val id: String,
        val title: String,
        val subtitle: String,
        val isActive: Boolean,
        var lastSync: String,
        val crop: String,
        val plantsLabel: String,
        val plantCount: Int,
        val sensors: List<PresenterZoneSensor>,
        // local
        var isCollapsed: Boolean
)