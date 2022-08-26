package com.arech.bloom.network.call;

import com.arech.bloom.models.Greenhouse;
import com.arech.bloom.network.BloomCallInterceptor;
import com.arech.bloom.network.callbacks.BloomCallback;
import com.arech.bloom.network.res.GreenhouseListResponse;

import java.util.List;

import javax.annotation.Nullable;

import retrofit2.Call;

/**
 * Created by Pili Arancibia on 8/18/19
 */
public class GreenhouseCall {
    public static void getAllFromNetwork(@Nullable final BloomCallback callback) {
        Call<GreenhouseListResponse> call = BloomCallInterceptor.service.getAllGreenhouses();

        BloomCallInterceptor.processCallList(call, callback);
    }
}
