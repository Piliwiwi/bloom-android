package com.arech.bloom.app.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.arech.bloom.BuildConfig
import com.arech.bloom.R
import com.arech.bloom.app.forgotpassword.ForgotPasswordActivity
import com.arech.bloom.app.login.presenter.Interfaces.LoginPresenter
import com.arech.bloom.app.login.presenter.LoginPresenterImpl
import com.arech.bloom.app.register.RegisterActivity
import com.arech.bloom.utils.NavigationHelper
import com.arech.bloom.utils.Validator
import com.google.android.material.textfield.TextInputLayout

/**
 * Created by Pili Arancibia on 8/15/19
 */

class LoginActivity: AppCompatActivity(), LoginView {

    private var loginPresenter: LoginPresenter? = null

    private var loginEmail: EditText? = null
    private var loginPass: EditText? = null
    private var loginBtn: Button? = null
    private var inputLayoutUser: TextInputLayout? = null
    private var forgotPassword: TextView? = null

    private var emailCheck = false
    private var passCheck = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        loginPresenter = LoginPresenterImpl(this)

        loginEmail = findViewById(R.id.loginEmail)
        loginPass = findViewById(R.id.loginPass)
        loginBtn = findViewById(R.id.loginBtn)
        inputLayoutUser = findViewById(R.id.inputLayoutUser)
        forgotPassword = findViewById(R.id.forgot_password)

        setListeners()

        if (BuildConfig.DEBUG) {
            loginEmail?.setText("msantelices@geogrow.cl")//"soledad@huertocalifornia.com")
            loginPass?.setText("1234")//"3132")
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            NavigationHelper.navToLanding(this)
            finish()
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun setListeners() {
        //Login button
        loginBtn?.setOnClickListener { login(loginEmail?.text.toString(), loginPass?.text.toString()) }

        //Email validation
        loginEmail?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable) {
                emailCheck = Validator.emailValidation(s.toString())
                manageEnableLoginButton()
                manageEmailErrorDisplay(emailCheck)
            }
        })

        //Pass empty detection
        loginPass?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable?) {
                passCheck = !s.toString().isEmpty()
                manageEnableLoginButton()
            }
        })

        forgotPassword?.setOnClickListener {
            val intent: Intent? = ForgotPasswordActivity.makeIntent(this)
            this.startActivity(intent)
        }
    }

    private fun manageEnableLoginButton() {
        loginBtn?.isEnabled = emailCheck && passCheck
    }

    private fun manageEmailErrorDisplay(check: Boolean) {
        if (check) inputLayoutUser?.isErrorEnabled = false
        else inputLayoutUser?.error = getString(R.string.invalid_email)
    }

    override fun login(email: String, password: String) {
        loginPresenter?.login(email, password)
    }

    override fun loginSuccess() {
        NavigationHelper.navToMain(this)
    }

    override fun errorHandler(error: Int) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

}