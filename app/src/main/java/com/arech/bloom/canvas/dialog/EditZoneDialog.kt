package com.arech.bloom.canvas.dialog

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.arech.bloom.R
import com.arech.bloom.app.zone.presenter.Constants
import kotlinx.android.synthetic.main.dialog_edit_zone.*


/**
 * Created by Pili Arancibia on 4/27/21.
 */

class EditZoneDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.dialog_edit_zone, null)
    }

    override fun onStart() {
        super.onStart()
        setupListeners()
        setupContent()
    }

    private val onAcceptClickListener = View.OnClickListener {
        sendResultOkToActivity()
        dismiss()
    }

    private fun sendResultOkToActivity() {
        try {
            val output = Intent()
            output.putExtra(Constants.DATA_ZONE_ID, arguments?.getString(ZONE_ID))
            output.putExtra(Constants.DATA_PLANT_COUNT, edit_plant_edit_text.text.toString().toInt())
            output.putExtra(Constants.DATA_CROP, edit_crop_edit_text.text.toString())
            targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, output)
        } catch (e: Exception) {

        }
    }

    fun setupListeners() {
        try {
            edit_zone_update_button.setOnClickListener(onAcceptClickListener)
        } catch (e: Exception) {

        }
    }

    private fun setupContent() {
        setPlantCount()
        setCrop()
    }

    private fun setPlantCount() {
        edit_plant_edit_text.setText(arguments?.getInt(PLANT_COUNT)?.toString())
    }

    private fun setCrop() {
        edit_crop_edit_text.setText(arguments?.getString(CROP))
    }

    companion object {
        private const val PLANT_COUNT = "plant_count"
        private const val CROP = "crop"
        private const val ZONE_ID = "zone_id"

        fun newInstance(id: String, plantCount: Int, crop: String): EditZoneDialog {
            val dialog = EditZoneDialog()
            val args = Bundle().apply {
                putString(ZONE_ID, id)
                putInt(PLANT_COUNT, plantCount)
                putString(CROP, crop)
            }

            dialog.arguments = args
            return dialog
        }
    }

}