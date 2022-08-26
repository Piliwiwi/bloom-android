package com.arech.bloom.app.fields.model

import com.arech.bloom.app.fields.model.interfaces.FieldInteractor
import com.arech.bloom.app.fields.presenter.interfaces.FieldPresenter

/**
 * Created by Pili Arancibia on 8/16/19
 */

class FieldInteractorImpl(var fieldPresenter: FieldPresenter): FieldInteractor {
    var fieldRepository = FieldRepositoryImpl(fieldPresenter)

    override fun getFields() {
        fieldRepository.getFields()
    }

}