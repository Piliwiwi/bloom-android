package com.arech.bloom.base

import android.app.Application
import android.content.Context
import com.arech.bloom.config.Config
import com.arech.bloom.config.Resources
import com.arech.bloom.core.BloomMigration
import com.arech.bloom.utils.LocaleHelper
import io.realm.Realm
import io.realm.RealmConfiguration
import java.util.*

/**
 * Created by Pili Arancibia on 8/16/19
 */

class BloomApp: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        initRealm()
        LocaleHelper.setLocale(baseContext, LocaleHelper.getLanguage(baseContext))
    }

    private fun initRealm() {
        Realm.init(this)
        val configuration = RealmConfiguration.Builder()
                .name(Resources.DATABASE_NAME)
                .schemaVersion(Config.DATABASE_SCHEMA_VERSION!!)
                .migration(BloomMigration())
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(configuration)
        Realm.getInstance(configuration)
    }

    companion object {

        @JvmStatic
        var date = Date()

        @JvmStatic
        var instance: BloomApp? = null
            private set

        @JvmStatic
        val appContext: Context
            get() = instance!!.getApplicationContext()

    }
}