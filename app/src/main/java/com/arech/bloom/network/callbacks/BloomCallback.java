package com.arech.bloom.network.callbacks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Pili Arancibia on 5/11/19
 */
public interface BloomCallback {
    void onSuccess(@Nullable Object value, int status);
    void onServerError(int status);
    void onError(@NonNull Throwable throwable);
}
