package com.arech.bloom.utils

import android.content.Context
import android.content.Intent
import com.arech.bloom.app.landing.LandingActivity
import com.arech.bloom.app.login.LoginActivity
import com.arech.bloom.app.main.MainActivity

/**
 * Created by Pili Arancibia on 8/15/19
 */

object NavigationHelper {
    @JvmStatic
    fun navToMain(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }

    @JvmStatic
    fun navToLogin(context: Context) {
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)
    }

    @JvmStatic
    fun navToLanding(context: Context) {
        val intent = Intent(context, LandingActivity::class.java)
        context.startActivity(intent)
    }
}