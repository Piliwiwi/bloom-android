package com.arech.bloom.network.call;

import androidx.annotation.Nullable;

import com.arech.bloom.app.zone.data.remote.model.RemoteZone;
import com.arech.bloom.app.zone.data.remote.model.ZoneEditRequest;
import com.arech.bloom.models.Zone;
import com.arech.bloom.network.BloomCallInterceptor;
import com.arech.bloom.network.callbacks.BloomCallback;
import com.arech.bloom.network.req.ZoneModificationRequest;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Pili Arancibia on 10/15/20
 */

public class ZoneCall {
    public static void getAllInFromNetwork(String sectorId, @Nullable final BloomCallback callback) {
        Call<List<Zone>> call = BloomCallInterceptor.service.getAllZonesInSector(sectorId);

        BloomCallInterceptor.processCallList(call, callback);
    }

    public static void getAllFromSectorFromNetwork(String sectorId, @Nullable final BloomCallback callback) {
        Call<List<RemoteZone>> call = BloomCallInterceptor.service.getAllZonesFromSector(sectorId);

        BloomCallInterceptor.processCallList(call, callback);
    }

    public static void setModification(String zoneId, ZoneModificationRequest request, @Nullable final BloomCallback callback) {
        Call<Void> call = BloomCallInterceptor.service.zoneModification(zoneId, request);

        BloomCallInterceptor.processCallList(call, callback);
    }

    public static void editZone(String zoneId, ZoneEditRequest request, @Nullable final BloomCallback callback) {
        Call<Void> call = BloomCallInterceptor.service.editZone(zoneId, request);

        BloomCallInterceptor.processCallList(call, callback);
    }
}
