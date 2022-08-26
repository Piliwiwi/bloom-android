package com.arech.bloom.app.login.model

import com.arech.bloom.app.login.model.Interfaces.LoginInteractor
import com.arech.bloom.app.login.presenter.Interfaces.LoginPresenter
import com.arech.bloom.network.req.LoginRequest

/**
 * Created by Pili Arancibia on 8/15/19
 */

class LoginInteractorImpl(var loginPresenter: LoginPresenter): LoginInteractor {
    private var loginRepository = LoginRepositoryImpl(loginPresenter)

    override fun login(request: LoginRequest) {
        loginRepository.login(request)
    }

}