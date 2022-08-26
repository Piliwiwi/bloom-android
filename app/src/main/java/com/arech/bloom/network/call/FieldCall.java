package com.arech.bloom.network.call;

import com.arech.bloom.models.Field;
import com.arech.bloom.network.BloomCallInterceptor;
import com.arech.bloom.network.callbacks.BloomCallback;

import java.util.List;

import javax.annotation.Nullable;

import retrofit2.Call;

/**
 * Created by Pili Arancibia on 5/11/19
 */
public class FieldCall {

    public static void getAllFromNetwork(@Nullable final BloomCallback callback) {
        Call<List<Field>> call = BloomCallInterceptor.service.getAllFields();

        BloomCallInterceptor.processCallList(call, callback);
    }

}
