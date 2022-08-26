package com.arech.bloom.network.call;

import androidx.annotation.Nullable;

import com.arech.bloom.models.Sensor;
import com.arech.bloom.models.Switch;
import com.arech.bloom.network.BloomCallInterceptor;
import com.arech.bloom.network.callbacks.BloomCallback;
import com.arech.bloom.network.req.SwitchModificationRequest;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Pili Arancibia on 9/28/19
 */

public class SwitchCall {
    public static void getAllInFromNetwork(String nodeId, @Nullable final BloomCallback callback) {
        Call<List<Switch>> call = BloomCallInterceptor.service.getAllInSwitches(nodeId);

        BloomCallInterceptor.processCallList(call, callback);
    }

    public static void getAllFromNetwork(@Nullable final BloomCallback callback) {
        Call<List<Switch>> call = BloomCallInterceptor.service.getAllSwitches();

        BloomCallInterceptor.processCallList(call, callback);
    }

    public static void setModification(String switchId, SwitchModificationRequest request, @Nullable final BloomCallback callback) {
        Call<Void> call = BloomCallInterceptor.service.switchModification(switchId, request);

        BloomCallInterceptor.processCallList(call, callback);
    }

    public static void getAllSwitchesInZoneFromNetwork(String sectorId, @Nullable final BloomCallback callback) {
        Call<List<Switch>> call = BloomCallInterceptor.service.getAllSwitchesInZone(sectorId);

        BloomCallInterceptor.processCallList(call, callback);
    }
}
