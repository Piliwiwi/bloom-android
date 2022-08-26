package com.arech.bloom.app.fields.model

import com.arech.bloom.app.fields.model.interfaces.FieldRepository
import com.arech.bloom.app.fields.presenter.interfaces.FieldPresenter
import com.arech.bloom.core.crud.UserDB

/**
 * Created by Pili Arancibia on 8/16/19
 */

class FieldRepositoryImpl(var fieldPresenter: FieldPresenter): FieldRepository {

    override fun getFields() {
        val content = arrayOf("")

        val me = UserDB.getMe()

        if (me != null) {

            content[0] += "BIENVENID@ " + me.name + "!" + "\n"


        } else {
            fieldPresenter.showFields("No tienes campos")
        }
    }

}