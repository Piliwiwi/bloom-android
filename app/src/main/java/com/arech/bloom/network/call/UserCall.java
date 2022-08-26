package com.arech.bloom.network.call;

import com.arech.bloom.models.User;
import com.arech.bloom.network.BloomCallInterceptor;
import com.arech.bloom.network.callbacks.BloomCallback;
import com.arech.bloom.network.req.DeviceRegistrationRequest;
import com.arech.bloom.network.req.LoginRequest;
import com.arech.bloom.network.res.LoginResponse;

import javax.annotation.Nullable;

import retrofit2.Call;

/**
 * Created by Pili Arancibia on 5/11/19
 */
public class UserCall {

    public static void login(LoginRequest request, @Nullable final BloomCallback callback) {
        Call<LoginResponse> call = BloomCallInterceptor.service.login(request);

        BloomCallInterceptor.processCall(call, callback);
    }

    public static void logout(@Nullable final BloomCallback callback) {
        Call<Void> call = BloomCallInterceptor.service.logout();

        BloomCallInterceptor.processCall(call, callback);
    }

    public static void getMe(@Nullable final BloomCallback callback) {
        Call<User> call = BloomCallInterceptor.service.getMe();

        BloomCallInterceptor.processCall(call, callback);
    }

    public static void sendDeviceRegistration(DeviceRegistrationRequest request, @Nullable final BloomCallback callback) {
        Call<Void> call = BloomCallInterceptor.service.deviceRegistration(request);

        BloomCallInterceptor.processCall(call, callback);
    }

}
