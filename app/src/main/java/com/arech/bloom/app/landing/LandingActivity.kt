package com.arech.bloom.app.landing

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.arech.bloom.R
import com.arech.bloom.app.register.RegisterActivity
import com.arech.bloom.utils.NavigationHelper

/**
 * Created by Pili Arancibia on 8/15/19
 */

class LandingActivity: AppCompatActivity() {
    private var landingLogin: Button? = null
    private var landingSub: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        supportActionBar!!.hide()

        landingLogin = findViewById(R.id.landinLogin)
        landingSub = findViewById(R.id.landingSub)

        setListeners()

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //do nothing
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun setListeners() {
        landingLogin?.setOnClickListener {
            NavigationHelper.navToLogin(this)
            finish()
        }
        landingSub?.setOnClickListener {
            val intent: Intent? = RegisterActivity.makeIntent(this)
            this.startActivity(intent)
        }
    }
}