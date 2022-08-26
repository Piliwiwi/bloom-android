package com.arech.bloom.app.fields

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.arech.bloom.R
import com.arech.bloom.app.fields.presenter.interfaces.FieldPresenter
import com.arech.bloom.app.fields.presenter.FieldPresenterImpl

/**
 * Created by Pili Arancibia on 8/16/19
 */

class FieldFragment: Fragment(), FieldView {

    private var fieldPresenter: FieldPresenter? = null

    private var fieldText: TextView? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fieldPresenter = FieldPresenterImpl(this)

        fieldText = view?.findViewById(R.id.fields_text)

        getFields()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_fields, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(): FieldFragment {
            val fragment = FieldFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getFields() {
        fieldPresenter?.getFields()
    }

    override fun showFields(content: String) {
        fieldText?.text = content
    }

}