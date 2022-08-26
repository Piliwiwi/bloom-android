package com.arech.bloom.config

import android.graphics.Color
import java.util.regex.Pattern

/**
 * Created by Pili Arancibia on 8/15/19
 */

object Resources {
    const val LOCAL_URL = "http://192.168.0.3:3000/"
    const val STAGING_URL = "http://ec2-18-228-43-17.sa-east-1.compute.amazonaws.com:3000/"
    const val DEV_URL = "http://ec2-18-231-197-107.sa-east-1.compute.amazonaws.com:4000/"
    const val BASE_URL = STAGING_URL
    const val DATABASE_NAME = "BloomDB"
    const val CURRENT_USER_IDENTIFIER = "me"
    const val KEY_TOKEN = "token"

    const val EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)+$"
    var emailPattern = Pattern.compile(EMAIL_PATTERN)

    const val INNACTIVE = "innactive"
    const val CRITIC_MAX = "critic_max"
    const val CRITIC_MIN = "critic_min"
    const val WARNING_MAX = "warning_max"
    const val WARNING_MIN = "warning_min"
    const val OPTIMUM = "optimum"
    const val OK = "ok"

    //default values
    const val DEF_STR = ""
    const val DEF_INT = -1

    val TRAFFIC_COLORS = intArrayOf(
            Color.rgb(228, 60, 61),
            Color.rgb(247, 237, 42),
            Color.rgb(77, 204, 66)
    )

    val SOFT_TRAFFIC_COLORS = intArrayOf(
            Color.rgb(126, 66, 73),
            Color.rgb(155, 182, 62),
            Color.rgb(50, 156, 77),
            Color.rgb(255, 174, 69),
            Color.rgb(64, 138, 247),
            Color.rgb(165, 165, 165)
    )

    object NetworkStatus {
        const val NOT_FOUND = 404
    }

}