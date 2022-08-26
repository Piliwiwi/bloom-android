package com.arech.bloom.app.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.arech.bloom.R
import com.arech.bloom.app.main.MainActivity
import com.arech.bloom.app.main.presenter.MainNav
import com.arech.bloom.core.crud.CompanyDB
import com.arech.bloom.core.crud.UserDB

/**
 * Created by Pili Arancibia on 8/18/19
 */

class SettingsFragment: Fragment() {
    private var name: TextView? = null
    private var email: TextView? = null
    private var title: TextView? = null
    private var companyLabel: TextView? = null
    private var phone: TextView? = null
    private var logout: Button? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        name = view?.findViewById(R.id.settings_name)
        email = view?.findViewById(R.id.settings_email)
        title = view?.findViewById(R.id.settings_title)
        companyLabel = view?.findViewById(R.id.settings_company)
        phone = view?.findViewById(R.id.settings_phone)
        logout = view?.findViewById(R.id.settings_logout)

        setUserInformation()
        setListeners()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(): SettingsFragment {
            val fragment = SettingsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    private fun setUserInformation() {
        val user = UserDB.getMe()
        val company = CompanyDB.getMyCompany()

        name?.text = user.name
        email?.text = user.email
        title?.text = user.title
        companyLabel?.text = company?.name ?: "No registra compañía"
        if(user?.phone == null) phone?.text = "Número desconocido"
        else phone?.text = "+56 " + user?.phone
    }

    private fun setListeners() {
        logout?.setOnClickListener {
            if (activity is MainActivity) {
                MainNav.logout(activity as MainActivity)
            }
        }
    }
}