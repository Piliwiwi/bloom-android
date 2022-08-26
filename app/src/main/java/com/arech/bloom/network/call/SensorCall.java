package com.arech.bloom.network.call;

import com.arech.bloom.models.Sensor;
import com.arech.bloom.network.BloomCallInterceptor;
import com.arech.bloom.network.callbacks.BloomCallback;

import java.util.List;

import androidx.annotation.Nullable;
import retrofit2.Call;

/**
 * Created by Pili Arancibia on 9/28/19
 */

public class SensorCall {
    public static void getAllInFromNetwork(String nodeId, @Nullable final BloomCallback callback) {
        Call<List<Sensor>> call = BloomCallInterceptor.service.getAllInSensors(nodeId);

        BloomCallInterceptor.processCallList(call, callback);
    }

    public static void getAllFromNetwork(@Nullable final BloomCallback callback) {
        Call<List<Sensor>> call = BloomCallInterceptor.service.getAllSensors();

        BloomCallInterceptor.processCallList(call, callback);
    }

    public static void getAllSensorsInZoneFromNetwork(String zoneId, @Nullable final BloomCallback callback) {
        Call<List<Sensor>> call = BloomCallInterceptor.service.getAllSensorsByZoneId(zoneId);

        BloomCallInterceptor.processCallList(call, callback);
    }
}
