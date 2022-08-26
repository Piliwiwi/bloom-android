package com.arech.bloom.network;

import android.util.Log;

import com.arech.bloom.network.callbacks.BloomCallback;

import java.util.List;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Pili Arancibia on 5/11/19
 */
public class BloomCallInterceptor {
    public static BloomService service = BloomClient.createService(BloomService.class);

    public static <classResponse> void processCall(Call call, @Nullable final BloomCallback callback) {
        call.enqueue(new Callback<classResponse>() {
            @Override
            public void onResponse(Call<classResponse> call, Response<classResponse> response) {
                Log.d("response", "Response Status: " + response.code() + " " + response.message());
                Log.d("response string", "Response Body: " + response.body());
                if(!response.isSuccessful()) {
                    if (callback != null) {
                        callback.onServerError(response.code());
                    }
                } else {
                    if (callback != null) {
                        callback.onSuccess(response.body(), response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<classResponse> call, Throwable t) {
                if (callback != null) {
                    callback.onError(t);
                }
            }
        });
    }

    public static <classResponse> void processCallList(Call call, @Nullable final BloomCallback callback) {
        call.enqueue(new Callback<List<classResponse>>() {
            @Override
            public void onResponse(Call<List<classResponse>> call, Response<List<classResponse>> response) {
                Log.d("response", "Response Status: " + response.code() + " " + response.message());
                Log.d("response string", "Response Body: " + response.body());
                if(!response.isSuccessful()) {
                    if (callback != null) {
                        callback.onServerError(response.code());
                    }
                } else {
                    if (callback != null) {
                        callback.onSuccess(response.body(), response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<classResponse>> call, Throwable t) {
                Log.e("response error", "Response ERROR message: " + t);
                Log.d("response error string", "Response: " + t.toString());
                if (callback != null) {
                    callback.onError(t);
                }
            }
        });
    }
}
