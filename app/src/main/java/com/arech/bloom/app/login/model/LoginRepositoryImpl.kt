package com.arech.bloom.app.login.model

import com.arech.bloom.R
import com.arech.bloom.app.login.model.Interfaces.LoginRepository
import com.arech.bloom.app.login.presenter.Interfaces.LoginPresenter
import com.arech.bloom.network.call.UserCall
import com.arech.bloom.network.callbacks.BloomCallback
import com.arech.bloom.network.req.LoginRequest
import com.arech.bloom.network.res.LoginResponse

/**
 * Created by Pili Arancibia on 8/15/19
 */

class LoginRepositoryImpl(var loginPresenter: LoginPresenter): LoginRepository {

    override fun login(request: LoginRequest) {
        UserCall.login(request, object : BloomCallback {
            override fun onSuccess(value: Any?, status: Int) {
                if(value is LoginResponse) {
                    val response = value as LoginResponse?
                    loginPresenter.loginSuccess(response!!)
                } else {
                    loginPresenter.errorHandler(R.string.internal_error)
                }
            }

            override fun onServerError(status: Int) {
                loginPresenter.errorHandler(R.string.wrong_password)
            }

            override fun onError(throwable: Throwable) {
                loginPresenter.errorHandler(R.string.net_error)
            }
        })
    }

}