package com.arech.bloom.config

/**
 * Created by Pili Arancibia on 8/16/19
 */

object Config {

    val NET_STATUS_CODE_OK = 200
    val DATABASE_SCHEMA_VERSION: Long? = 10L

    //Businees rules
    val VITALITY_CRITIC_THRESHOLD: Int = 35

    val SENSOR_MAX_SLOGAN = 0.3
    val SENSOR_MIN_SLOGAN = 0.3
    val SENSOR_OPTIMUM_SLOGAN = 0.1

    object Formats {
        const val DATE_FORMAT = "dd/M/yyyy HH:mm"
    }
}