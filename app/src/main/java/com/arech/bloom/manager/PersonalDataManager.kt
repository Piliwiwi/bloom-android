package com.arech.bloom.manager

import android.content.Context
import android.util.Log
import androidx.annotation.Nullable
import com.arech.bloom.app.main.MainActivity
import com.arech.bloom.base.FragmentInterface
import com.arech.bloom.core.crud.*
import com.arech.bloom.models.*
import com.arech.bloom.network.call.*
import com.arech.bloom.network.callbacks.BloomCallback
import com.arech.bloom.network.callbacks.NetworkCallback

object PersonalDataManager {
    @JvmStatic
    fun downloadPersonalInformation(context: Context) {
        println("loading data...")

        downloadMe(object : NetworkCallback {
            override fun onSuccess(ids: List<String>) {
                downloadMyCompany(object: NetworkCallback {
                    override fun onSuccess(ids: List<String>) {
                        done(context)
                    }
                })
            }
        })
    }

    @JvmStatic
    fun done(context: Context) {
        if(context is MainActivity) {
            context.processCustoms()
            if(context.visibleFragment != null && context.visibleFragment is FragmentInterface) {
                (context.visibleFragment as FragmentInterface).updateUI()
                println("Update UI!")
            }
        }
        println("Data is loaded!")
    }

    @JvmStatic
    fun downloadMe(@Nullable callback: NetworkCallback) {
        UserCall.getMe(object : BloomCallback {
            override fun onSuccess(value: Any?, status: Int) {
                val response: User = value as User
                UserDB.add(response)
                callback.onSuccess(emptyList())
            }

            override fun onServerError(status: Int) {
                Log.d("SERVER ERROR ME: ", "SERVER ERROR : " + status)
                callback.onSuccess(emptyList())
            }

            override fun onError(throwable: Throwable) {
                throwable.printStackTrace()
                callback.onSuccess(emptyList())
            }
        })
    }

    @JvmStatic
    fun downloadMyCompany(@Nullable callback: NetworkCallback) {
        CompanyCall.getMyCompany(object : BloomCallback {
            override fun onSuccess(value: Any?, status: Int) {
                val response: Company = value as Company
                Log.d("COMPANY RESPONSE: ", "COMPANY RESPONSE : " + response)
                CompanyDB.add(response)
                callback.onSuccess(emptyList())
            }

            override fun onServerError(status: Int) {
                Log.d("SERVER ERROR COMPANY: ", "SERVER ERROR : " + status)
                callback.onSuccess(emptyList())
            }

            override fun onError(throwable: Throwable) {
                throwable.printStackTrace()
                callback.onSuccess(emptyList())
            }
        })
    }
}