package com.arech.bloom.network.call;

import com.arech.bloom.models.Company;
import com.arech.bloom.network.BloomCallInterceptor;
import com.arech.bloom.network.callbacks.BloomCallback;

import javax.annotation.Nullable;

import retrofit2.Call;

public class CompanyCall {
    public static void getMyCompany(@Nullable final BloomCallback callback) {
        Call<Company> call = BloomCallInterceptor.service.getMyCompany();

        BloomCallInterceptor.processCall(call, callback);
    }
}
