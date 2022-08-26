package com.arech.bloom.app.forgotpassword.insertemail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arech.bloom.R
import com.arech.bloom.databinding.FragmentForgotPasswordEmailBinding
import com.arech.bloom.utils.Validator

class InsertEmailFragment : Fragment() {
    private var binding: FragmentForgotPasswordEmailBinding? = null
    private var emailCheck = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentForgotPasswordEmailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        binding?.apply {
            email.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

                override fun afterTextChanged(s: Editable) {
                    emailCheck = Validator.emailValidation(s.toString())
                    manageEmailErrorDisplay(emailCheck)
                }
            })
        }
    }

    private fun manageEmailErrorDisplay(check: Boolean) {
        binding?.apply {
            if (check) emailLayout.isErrorEnabled = false
            else emailLayout.error = getString(R.string.invalid_email)
        }
    }
}