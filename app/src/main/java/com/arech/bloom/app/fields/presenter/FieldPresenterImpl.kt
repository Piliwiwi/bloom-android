package com.arech.bloom.app.fields.presenter

import com.arech.bloom.app.fields.FieldView
import com.arech.bloom.app.fields.model.FieldInteractorImpl
import com.arech.bloom.app.fields.presenter.interfaces.FieldPresenter

/**
 * Created by Pili Arancibia on 8/15/19
 */

class FieldPresenterImpl(var fieldView: FieldView): FieldPresenter {
    private var fieldInteractor = FieldInteractorImpl(this)

    override fun getFields() {
        fieldInteractor.getFields()
    }

    override fun showFields(content: String) {
        fieldView.showFields(content)
    }

}