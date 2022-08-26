package com.arech.bloom.app.zone.presenter.mapper

import com.arech.bloom.app.zone.data.remote.model.RemoteZone
import com.arech.bloom.app.zone.data.remote.model.RemoteZoneSensor
import com.arech.bloom.app.zone.data.remote.model.RemoteZonesResponse
import com.arech.bloom.app.zone.presenter.model.PresenterZone
import com.arech.bloom.app.zone.presenter.model.PresenterZoneSensor
import com.arech.bloom.app.zone.presenter.model.PresenterZones
import com.arech.bloom.config.Resources

/**
 * Created by Pili Arancibia on 4/24/21.
 */

class PresenterZonesMapper {

    fun RemoteZonesResponse.fromRemoteToPresenter() = PresenterZones(
            zones = zones?.map { zone -> zone.fromRemoteToPresenter() }.orEmpty()
    )

    fun RemoteZone.fromRemoteToPresenter() = PresenterZone(
            id = id.orEmpty(),
            title = title.orEmpty(),
            subtitle = subtitle.orEmpty(),
            isActive = isActive ?: false,
            lastSync = lastSync.orEmpty(),
            crop = crop.orEmpty(),
            plantsLabel = plantsLabel.orEmpty(),
            plantCount = plantCount ?: 0,
            sensors = sensors?.map { sensor -> sensor.fromRemoteToPresenter() }.orEmpty(),
            isCollapsed = true
    )

    private fun RemoteZoneSensor.fromRemoteToPresenter() = PresenterZoneSensor(
            id = id.orEmpty(),
            type = type.orEmpty(),
            formattedData = formattedData.orEmpty(),
            formattedPermittedMax = formattedPermittedMax.orEmpty(),
            formattedPermittedMin = formattedPermittedMin.orEmpty(),
            formattedOptimum = formattedOptimum.orEmpty(),
            medium = medium.orEmpty(),
            icon = icon.orEmpty(),
            stat = stat ?: Resources.INNACTIVE
    )

}