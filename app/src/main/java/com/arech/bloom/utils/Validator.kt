package com.arech.bloom.utils

import com.arech.bloom.config.Resources

/**
 * Created by Pili Arancibia on 8/15/19
 */

object Validator {
    @JvmStatic
    fun emailValidation(email: String): Boolean {
        val emailMatcher = Resources.emailPattern.matcher(email)
        return emailMatcher.matches()
    }
}
