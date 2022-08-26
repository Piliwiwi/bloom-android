package com.arech.bloom.app.login

/**
 * Created by Pili Arancibia on 8/15/19
 */

interface LoginView {
    fun login(email: String, password: String)
    fun errorHandler(error: Int)
    fun loginSuccess()
}