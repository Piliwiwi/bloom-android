package com.arech.bloom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.arech.bloom.R
import com.arech.bloom.app.zone.presenter.model.PresenterZoneSensor
import com.arech.bloom.config.Resources
import com.arech.bloom.models.Node
import com.arech.bloom.models.Sensor
import com.arech.bloom.utils.round

/**
 * Created by Pili Arancibia on 9/28/19
 */

class SensorAdapter(context: Context, isCollapsed: Boolean, sensors: List<PresenterZoneSensor>, expanded: Boolean) : RecyclerView.Adapter<SensorAdapter.ViewHolder>() {
    private var activity = context
    private var data = sensors
    private var isCollapsed = isCollapsed
    private var expanded = expanded

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.cell_sensor, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sensor = data[position]
        holder.data?.text = sensor.formattedData
        holder.max?.text = sensor.formattedPermittedMax
        holder.min?.text = sensor.formattedPermittedMin
        holder.optimum?.text = sensor.formattedOptimum
        holder.medium?.visibility = if (sensor.medium.isEmpty()) View.GONE else View.VISIBLE
        holder.mode?.text = sensor.type
        holder.medium?.text = sensor.medium

        when {
            expanded -> {
                holder.details?.visibility = View.VISIBLE
                holder.names?.visibility = View.VISIBLE

                val paramsForColor = LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.MATCH_PARENT, 1f
                )
                val paramsForRight = LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 5f
                )

                holder.color?.layoutParams = paramsForColor
                holder.right_frame?.layoutParams = paramsForRight
            }
            isCollapsed -> {
                holder.details?.visibility = View.GONE
                holder.names?.visibility = View.GONE
                val paramsForColor = LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.MATCH_PARENT, 1f
                )
                val paramsForRight = LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, if (data.size > 1) 2f else 5f
                )

                holder.color?.layoutParams = paramsForColor
                holder.right_frame?.layoutParams = paramsForRight
            }
            else -> {
                holder.details?.visibility = View.VISIBLE
                holder.names?.visibility = View.VISIBLE

                val paramsForColor = LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.MATCH_PARENT, 1f
                )
                val paramsForRight = LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 5f
                )

                holder.color?.layoutParams = paramsForColor
                holder.right_frame?.layoutParams = paramsForRight
            }
        }
        setIcons(holder, sensor)
        calculateColors(holder, sensor)
        stylize(holder)
    }

    private fun stylize(holder: ViewHolder) {
        if (expanded) {
            holder.details?.background = ContextCompat.getDrawable(activity, R.drawable.layout_rounded_right)
        } else {
            holder.details?.background = ContextCompat.getDrawable(activity, R.color.greenhouse_default_bg)
        }
    }

    private fun setIcons(holder: ViewHolder, sensor: PresenterZoneSensor) {
        var icon = R.drawable.ic_warning
        when (sensor.icon) {
            "ic_humidity" -> icon = R.drawable.ic_humidity
            "ic_pressure" -> icon = R.drawable.ic_pressure
            "ic_temperature" -> icon = R.drawable.ic_temperature
            "ic_soil_humidity" -> icon = R.drawable.ic_soilhumidity
            "ic_soil_temperature" -> icon = R.drawable.ic_soiltemperture
        }
        holder.icon?.setImageResource(icon)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun calculateColors(holder: ViewHolder, sensor: PresenterZoneSensor) {
        val unwrappedDrawable = AppCompatResources.getDrawable(activity, R.drawable.btn_rounded)
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)

        val color = when (sensor.stat) {
            Resources.INNACTIVE -> R.color.innactive
            Resources.CRITIC_MAX -> R.color.sensor_critic_max
            Resources.CRITIC_MIN -> R.color.sensor_critic_min
            Resources.WARNING_MAX -> R.color.sensor_warning_max
            Resources.WARNING_MIN -> R.color.sensor_warning_min
            else -> R.color.sensor_ok
        }

        val maxColor = when (sensor.stat) {
            Resources.CRITIC_MAX -> R.color.critic_max_bg
            Resources.WARNING_MAX -> R.color.warning_max_bg
            else -> android.R.color.transparent
        }

        val minColor = when (sensor.stat) {
            Resources.CRITIC_MIN -> R.color.critic_min_bg
            Resources.WARNING_MIN -> R.color.warning_min_bg
            else -> android.R.color.transparent
        }

        val optimumColor = if (sensor.stat == Resources.OPTIMUM) R.color.ok_bg
        else android.R.color.transparent

        // TODO: sync this with backend
//        if (sensor.stat == Resources.INNACTIVE) {
//            holder.data?.text = "NC"
//        }

        holder.max_bg?.setBackgroundColor(activity.resources.getColor(maxColor))
        holder.min_bg?.setBackgroundColor(activity.resources.getColor(minColor))
        holder.optimum_bg?.setBackgroundColor(activity.resources.getColor(optimumColor))

        DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(activity, color))

        holder.color?.background = wrappedDrawable
        holder.data?.setTextColor(activity.resources.getColor(color))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var data: TextView? = null
        var max: TextView? = null
        var min: TextView? = null
        var mode: TextView? = null
        var medium: TextView? = null
        var optimum: TextView? = null
        var details: LinearLayout? = null
        var names: LinearLayout? = null
        var color: LinearLayout? = null
        var max_bg: LinearLayout? = null
        var optimum_bg: LinearLayout? = null
        var min_bg: LinearLayout? = null
        var right_frame: LinearLayout? = null
        var icon: ImageView? = null
        var frame_top: FrameLayout? = null

        init {
            this.data = itemView.findViewById(R.id.sensor_data) as TextView
            this.max = itemView.findViewById(R.id.sensor_max) as TextView
            this.min = itemView.findViewById(R.id.sensor_min) as TextView
            this.mode = itemView.findViewById(R.id.sensor_mode) as TextView
            this.medium = itemView.findViewById(R.id.sensor_medium) as TextView
            this.optimum = itemView.findViewById(R.id.sensor_optimum) as TextView
            this.details = itemView.findViewById(R.id.cell_sensor_details) as LinearLayout
            this.names = itemView.findViewById(R.id.sensor_names) as LinearLayout
            this.color = itemView.findViewById(R.id.sensor_color) as LinearLayout
            this.max_bg = itemView.findViewById(R.id.sensor_max_bg) as LinearLayout
            this.optimum_bg = itemView.findViewById(R.id.sensor_optimum_bg) as LinearLayout
            this.min_bg = itemView.findViewById(R.id.sensor_min_bg) as LinearLayout
            this.right_frame = itemView.findViewById(R.id.right_frame) as LinearLayout
            this.icon = itemView.findViewById(R.id.sensor_icon) as ImageView
            this.frame_top = itemView.findViewById(R.id.cell_sensor_frame_top) as FrameLayout
        }
    }
}