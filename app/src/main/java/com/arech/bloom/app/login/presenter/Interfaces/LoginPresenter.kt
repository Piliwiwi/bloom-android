package com.arech.bloom.app.login.presenter.Interfaces

import com.arech.bloom.network.res.LoginResponse

/**
 * Created by Pili Arancibia on 8/15/19
 */

interface LoginPresenter {
    fun login(email: String, password: String)
    fun errorHandler(error: Int)
    fun loginSuccess(response: LoginResponse)
}