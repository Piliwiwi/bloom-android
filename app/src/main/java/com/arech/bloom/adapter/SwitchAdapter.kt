package com.arech.bloom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.arech.bloom.R
import com.arech.bloom.core.crud.SwitchDB
import com.arech.bloom.core.crud.UserDB
import com.arech.bloom.models.Switch
import com.arech.bloom.network.call.SwitchCall
import com.arech.bloom.network.callbacks.BloomCallback
import com.arech.bloom.network.req.SwitchModificationRequest

/**
 * Created by Pili Arancibia on 1/11/20
 */

class SwitchAdapter(context: Context, switches: List<Switch>): RecyclerView.Adapter<SwitchAdapter.ViewHolder>() {
    private var listdata: List<Switch> = switches
    private var activity = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.cell_switch, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val maSwitch = listdata[position]
        viewBuild(holder, maSwitch)
        clickEvent(holder, maSwitch, position)
    }

    override fun getItemCount(): Int {
        return listdata.size
    }

    private fun clickEvent(holder: ViewHolder, maSwitch: Switch, position: Int) {
        holder.isAuto?.setOnCheckedChangeListener { buttonView, isChecked ->
            val request = SwitchModificationRequest(maSwitch.isActive, isChecked)
            SwitchCall.setModification(maSwitch._id, request, object : BloomCallback {
                override fun onSuccess(value: Any?, status: Int) {
                    listdata[position].isAuto = isChecked
                    holder.isOn?.isClickable = !isChecked

                }
                override fun onServerError(status: Int) {}
                override fun onError(throwable: Throwable) {}
            })
        }

        holder.isOn?.setOnCheckedChangeListener { buttonView, isChecked ->
            val request = SwitchModificationRequest(isChecked, maSwitch.isAuto)
            SwitchCall.setModification(maSwitch._id, request, object : BloomCallback {
                override fun onSuccess(value: Any?, status: Int) {
                    listdata[position].isActive = isChecked
                }
                override fun onServerError(status: Int) {}
                override fun onError(throwable: Throwable) {}
            })
        }
    }

    private fun viewBuild(holder: ViewHolder, maSwitch: Switch) {
        holder.isAuto?.setOnCheckedChangeListener(null)
        holder.isOn?.setOnCheckedChangeListener(null)
        holder.name?.text = maSwitch.name?: maSwitch.mode
        holder.isAuto?.isChecked = maSwitch.isAuto
        holder.isOn?.isChecked = maSwitch.isActive
        val unwrappedDrawable = AppCompatResources.getDrawable(activity, R.drawable.layout_rounded)
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)

        setIcons(holder, maSwitch)

        if(!checkPermissions(holder)) {
            holder.isAuto?.isClickable = false
            holder.isOn?.isClickable = false

            DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(activity, R.color.bloom_bg_innactive))
            holder.bg?.background = wrappedDrawable
            return
        }

        DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(activity, R.color.bloom_bg_clear))
        holder.bg?.background = wrappedDrawable

        holder.isOn?.isClickable = !maSwitch.isAuto
    }

    private fun setIcons(holder: ViewHolder, maSwitch: Switch) {
        var icon = R.drawable.ic_warning
        when(maSwitch.mode) {
            "airing" -> icon = R.drawable.ic_fan
            "light" -> icon = R.drawable.ic_led
            "heating" -> icon = R.drawable.ic_heating
            "irrigation" -> icon = R.drawable.ic_irrigation
        }
        holder.icon?.setImageResource(icon)
    }

    private fun checkPermissions(holder: ViewHolder): Boolean {
        val me = UserDB.getMe()

        return me?.role != "readOnly"
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView? = null
        var isAuto: android.widget.Switch? = null
        var isOn: ToggleButton? = null
        var bg: LinearLayout? = null
        var icon: ImageView? = null

        init {
            this.name = itemView.findViewById(R.id.cell_switch_name) as TextView
            this.isAuto = itemView.findViewById(R.id.cell_switch_auto) as android.widget.Switch
            this.isOn = itemView.findViewById(R.id.cell_switch_on) as ToggleButton
            this.bg = itemView.findViewById(R.id.cell_switch_bg) as LinearLayout
            this.icon = itemView.findViewById(R.id.cell_switch_icon) as ImageView
        }
    }
}