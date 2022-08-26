package com.arech.bloom.app.login.presenter

import com.arech.bloom.R
import com.arech.bloom.app.login.LoginView
import com.arech.bloom.app.login.model.LoginInteractorImpl
import com.arech.bloom.app.login.presenter.Interfaces.LoginPresenter
import com.arech.bloom.config.Resources
import com.arech.bloom.core.crud.UserDB
import com.arech.bloom.core.crud.UserPreferencesDB
import com.arech.bloom.models.utils.UserPreferences
import com.arech.bloom.network.BloomCallInterceptor
import com.arech.bloom.network.BloomClient
import com.arech.bloom.network.BloomService
import com.arech.bloom.network.req.LoginRequest
import com.arech.bloom.network.res.LoginResponse
import com.arech.bloom.utils.Validator

/**
 * Created by Pili Arancibia on 8/15/19
 */

class LoginPresenterImpl(var loginView: LoginView): LoginPresenter {

    private var loginInteractor = LoginInteractorImpl(this)

    override fun login(email: String, password: String) {
        if (!Validator.emailValidation(email)) {
            loginView.errorHandler(R.string.invalid_email)
        } else if (email.isNotEmpty() && password.isNotEmpty()) {
            val request = LoginRequest(email, password, true)
            loginInteractor.login(request)
        } else {
            loginView.errorHandler(R.string.empty_fields)
        }
    }

    override fun loginSuccess(response: LoginResponse) {
        val preferences = UserPreferences(Resources.CURRENT_USER_IDENTIFIER, response.user._id, response.token)
        UserPreferencesDB.save(preferences)
        UserDB.add(response.user)
        BloomCallInterceptor.service = BloomClient.createService(BloomService::class.java, UserPreferencesDB.getToken())
        loginView.loginSuccess()
    }

    override fun errorHandler(error: Int) {
        loginView.errorHandler(error)
    }
}