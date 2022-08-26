package com.arech.bloom.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arech.bloom.R
import com.arech.bloom.app.main.MainActivity
import com.arech.bloom.app.zone.presenter.Constants
import com.arech.bloom.app.zone.presenter.model.AttrsEventSensor
import com.arech.bloom.app.zone.presenter.model.PresenterZone
import com.arech.bloom.canvas.dialog.EditZoneDialog
import com.arech.bloom.core.crud.*
import com.arech.bloom.models.Zone
import com.arech.bloom.network.call.ZoneCall
import com.arech.bloom.network.callbacks.BloomCallback
import com.arech.bloom.network.req.ZoneModificationRequest

/**
 * Created by Pili Arancibia on 6/17/20
 */

class ZoneAdapter(activity: Activity, fragment: Fragment, zones: List<PresenterZone>): RecyclerView.Adapter<ZoneAdapter.ViewHolder>() {
    private var listdata: List<PresenterZone> = zones
    private var activity = activity
    private var fragment = fragment
    private var holder: ViewHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.cell_bed, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val zone = listdata[position]
        viewBuild(holder, zone)
        clickEvent(holder, zone, position)
        this.holder = holder
    }

    override fun getItemCount(): Int {
        return listdata.size
    }

    private fun clickEvent(holder: ViewHolder, zone: PresenterZone, position: Int) {
        /** Don't click in recyclerview **/
        holder.sensorRecyclerView?.isLayoutFrozen = true

        holder.frame_top?.setOnClickListener {
            zone.isCollapsed = !zone.isCollapsed
            notifyItemChanged(position)
        }

        holder.frame_bottom?.setOnClickListener {
            if(activity is MainActivity) {
                (activity as MainActivity).goToZoneSwitches(zone)
            }
        }

        holder.frame_bottom?.setOnLongClickListener {
            val dialog = EditZoneDialog.newInstance(zone.id, zone.plantCount, zone.crop).apply {
                setTargetFragment(
                        fragment,
                        Constants.EDIT_ZONE_CODE
                )
            }

            fragment.fragmentManager?.apply { dialog.show(this, dialog.tag) }
            true
        }

        // TODO: not use data in presentation layer
        holder.active_button?.setOnCheckedChangeListener { buttonView, isChecked ->
            val request = ZoneModificationRequest(isChecked)
            ZoneCall.setModification(zone.id, request, object : BloomCallback {
                override fun onSuccess(value: Any?, status: Int) {
                    zone.isCollapsed = isChecked
                }
                override fun onServerError(status: Int) {
                    Log.e("SERVER ERROR", "ZONE MODIFICATION ERROR - status: " + status)
                }
                override fun onError(throwable: Throwable) {
                    Log.e("ERROR", "ZONE MODIFICATION ERROR")
                }
            })
        }
    }

    private fun viewBuild(holder: ViewHolder, zone: PresenterZone) {
        this.holder = holder

        prepareView(holder, zone)

        holder.active_button?.setOnCheckedChangeListener(null)
        holder.name?.text = zone.title
        holder.active_button?.isChecked = zone.isActive
        holder.subtitle?.text = zone.subtitle
        holder.plants?.text = zone.plantsLabel
        holder.number?.text = zone.lastSync
    }

    private fun prepareView(holder: ViewHolder?, zone: PresenterZone) {
        prepareSensors(holder, zone)
    }

    private fun prepareSensors(holder: ViewHolder?, zone: PresenterZone) {
        val sensors = zone.sensors
        val adapter = SensorAdapter(activity, zone.isCollapsed, sensors, false)
        holder?.sensorRecyclerView?.setHasFixedSize(true)
        var grid = 2
        if (zone.isCollapsed) grid = 4
        if (sensors.size == 1) grid = 1
        holder?.sensorRecyclerView?.layoutManager = GridLayoutManager(activity, grid)
        holder?.sensorRecyclerView?.adapter = adapter
    }

    fun onNodeEvent(zonesId: ArrayList<String>?, lastSync: String?) {
        zonesId?.apply {
            lastSync?.apply {
                listdata.filter { zonesId.contains(it.id) }.map { eventZone ->
                    val position = listdata.indexOfFirst { z -> z.id == eventZone.id }
                    listdata[position].lastSync = lastSync
                    notifyItemChanged(position)
                }
            }
        }
    }

    fun onSensorEvent(event: AttrsEventSensor) {
        event.zoneId?.apply {
            listdata.firstOrNull { z -> z.id == this }?.let { eventZone ->
                val position = listdata.indexOfFirst { z -> z.id == this }
                eventZone.sensors.first { s -> s.id == event.id }.let { eventSensor ->
                    val sensorPosition = listdata[position].sensors.indexOfFirst { s -> s.id == eventSensor.id }
                    if (event.formattedData != null) listdata[position].sensors[sensorPosition].formattedData = event.formattedData
                    if (event.formattedStat != null) listdata[position].sensors[sensorPosition].stat = event.formattedStat
                    notifyItemChanged(position)
                }
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView? = null
        var subtitle: TextView? = null
        var number: TextView? = null
        var plants: TextView? = null
        var sensorRecyclerView: RecyclerView? = null
        var frame: LinearLayout? = null
        var frame_top: LinearLayout? = null
        var frame_bottom: LinearLayout? = null
        var second_frame: LinearLayout? = null
        var active_button: ToggleButton? = null

        init {
            this.name = itemView.findViewById(R.id.cell_bed_name) as TextView
            this.subtitle = itemView.findViewById(R.id.cell_bed_subtitle) as TextView
            this.number = itemView.findViewById(R.id.cell_bed_number) as TextView
            this.plants = itemView.findViewById(R.id.cell_bed_plants) as TextView
            this.sensorRecyclerView = itemView.findViewById(R.id.bed_sensor_recyclerview)
            this.frame = itemView.findViewById(R.id.cell_bed_frame) as LinearLayout
            this.frame_top = itemView.findViewById(R.id.cell_bed_bg) as LinearLayout
            this.frame_bottom = itemView.findViewById(R.id.cell_bed_title) as LinearLayout
            this.second_frame = itemView.findViewById(R.id.second_layout) as LinearLayout
            this.active_button = itemView.findViewById(R.id.cell_zone_is_active) as ToggleButton
        }
    }
}