package com.arech.bloom.app.login.model.Interfaces

import com.arech.bloom.network.req.LoginRequest

/**
 * Created by Pili Arancibia on 8/15/19
 */

interface LoginRepository {

    fun login(request: LoginRequest)
}