package com.arech.bloom.network.call;

import com.arech.bloom.models.Sector;
import com.arech.bloom.network.BloomCallInterceptor;
import com.arech.bloom.network.callbacks.BloomCallback;
import com.arech.bloom.network.res.BloomServerListResponse;

import java.util.List;

import androidx.annotation.Nullable;
import retrofit2.Call;

/**
 * Created by Pili Arancibia on 9/27/19
 */
public class SectorCall {

    public static void getAllInFromNetwork(String greenhouseId, @Nullable final BloomCallback callback) {
        Call<BloomServerListResponse> call = BloomCallInterceptor.service.getAllSectorFromGreenhouseBFF(greenhouseId);

        BloomCallInterceptor.processCallList(call, callback);
    }

    public static void getAllFromNetwork(@Nullable final BloomCallback callback) {
        Call<List<Sector>> call = BloomCallInterceptor.service.getAllSectors();

        BloomCallInterceptor.processCallList(call, callback);
    }
}
