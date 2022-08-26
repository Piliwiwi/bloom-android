package com.arech.bloom.app.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.ui.setupActionBarWithNavController
import com.arech.bloom.R
import com.arech.bloom.databinding.ActivityRegisterBinding

class RegisterActivity: AppCompatActivity() {
    private var binding: ActivityRegisterBinding? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        navController = Navigation.findNavController(this, R.id.register_nav_fragment)
        setupActivity()
    }

    private fun setupActivity() {
        setupNavigation()
        destinationManager()
    }

    private fun setupNavigation() {
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setSupportActionBar(binding?.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun destinationManager() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
            when (destination.id) {

            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        fun makeIntent(context: Context): Intent = Intent(context, RegisterActivity::class.java)
    }
}