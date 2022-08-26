package com.arech.bloom.utils

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.arech.bloom.network.ConnectionLiveData

object ObservableInternetConnection {
    private var connectionLiveData: ConnectionLiveData? = null
    private var isOnlineLiveData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        connectionLiveData = null
    }

    fun start(context: Context) {
        connectionLiveData = ConnectionLiveData(context)
        connectionLiveData?.observeForever { connection ->
            isOnlineLiveData.value = connection
        }
    }

    fun getIsOnlineLiveData() = isOnlineLiveData
}