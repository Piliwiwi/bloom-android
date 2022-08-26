package com.arech.bloom.app.register.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.arech.bloom.R
import com.arech.bloom.databinding.FragmentRegisterSignupBinding
import com.arech.bloom.utils.Validator
import kotlinx.android.synthetic.main.activity_main.view.*

class SignUpFragment: Fragment() {
    private var binding: FragmentRegisterSignupBinding? = null
    private var nameText = ""
    private var emailText = ""
    private var pass = ""
    private var repeatPass = ""
    private var emailCheck = false
    private var passCheck = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentRegisterSignupBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        binding?.apply {

            names.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

                override fun afterTextChanged(s: Editable) {
                    nameText = s.toString()
                }
            })

            email.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

                override fun afterTextChanged(s: Editable) {
                    emailText = s.toString()
                    emailCheck = Validator.emailValidation(s.toString())
                    manageEmailErrorDisplay(emailCheck)
                }
            })

            password.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

                override fun afterTextChanged(s: Editable?) {
                    pass = s.toString()
                    passCheck = repeatPass == pass
                    managePasswordsErrorDisplay(passCheck)
                }
            })

            repeatPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

                override fun afterTextChanged(s: Editable?) {
                    repeatPass = s.toString()
                    passCheck = repeatPass == pass
                    managePasswordsErrorDisplay(passCheck)
                }
            })

            btnRegistration.setOnClickListener {
                if (areEmptyFields()) {
                    Toast.makeText(context, "¡Aún tienes campos sin llenar!", Toast.LENGTH_LONG).show()
                } else if (!emailCheck) {
                    Toast.makeText(context, "¡Tienes que ingresar correctamente tu correo electrónico!", Toast.LENGTH_LONG).show()
                } else if (!passCheck) {
                    Toast.makeText(context, "¡Tus contraseñas no coinciden!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Hola aún no hago esto", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun areEmptyFields(): Boolean {
        return nameText.isEmpty() || emailText.isEmpty() || pass.isEmpty() || repeatPass.isEmpty()
    }

    private fun managePasswordsErrorDisplay(check: Boolean) {
        binding?.apply {
            if (check) {
                passwordLayout.isErrorEnabled = false
                repeatPasswordLayout.isErrorEnabled = false
            }
            else {
                passwordLayout.error = "Las contraseñas no coinciden"
                repeatPasswordLayout.error = "Las contraseñas no coinciden"
            }
        }
    }

    private fun manageEmailErrorDisplay(check: Boolean) {
        binding?.apply {
            if (check) emailLayout.isErrorEnabled = false
            else emailLayout.error = getString(R.string.invalid_email)
        }
    }

}