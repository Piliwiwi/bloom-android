package com.arech.bloom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arech.bloom.R
import com.arech.bloom.app.main.MainActivity
import com.arech.bloom.core.crud.NodeDB
import com.arech.bloom.core.crud.SensorDB
import com.arech.bloom.core.crud.SwitchDB
import com.arech.bloom.models.Node
import com.arech.bloom.models.Sensor
import com.arech.bloom.network.call.SensorCall
import com.arech.bloom.network.callbacks.BloomCallback
import java.text.SimpleDateFormat

/**
 * Created by Pili Arancibia on 9/28/19
 */

class NodeAdapter(context: Context, nodes: List<Node>): RecyclerView.Adapter<NodeAdapter.ViewHolder>() {
    private var listdata: List<Node> = nodes.sortedBy { node -> node.crop }
    private var activity = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.cell_node, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val node = listdata[position]


        viewBuild(holder, node)
        clickEvent(holder, node, position)
    }

    override fun getItemCount(): Int {
        return listdata.size
    }

    private fun clickEvent(holder: ViewHolder, node: Node, position: Int) {
        /** Don't click in recyclerview **/
        holder.sensorRecyclerView?.isLayoutFrozen = true

        holder.frame_top?.setOnClickListener {
            NodeDB.setCollapse(node._id, !node.isCollapsed)
            notifyItemChanged(position)
        }
    }

    private fun viewBuild(holder: ViewHolder, node: Node) {
        holder.name?.text = "Nodo " + node.number + ": " + (node.crop ?: "Sin nombre")
        //holder.number?.text = activity.getString(R.string.node) + " " + node.number.toString()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss a")
        val currentDate = sdf.format(node.lastSync)
        holder.number?.text = currentDate

        val unwrappedDrawable = AppCompatResources.getDrawable(activity, R.drawable.layout_rounded_top)
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
        DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(activity, R.color.greenhouse_default_bg))

        /* PATCH */
        val sensors = SensorDB.getAllFromNode(node._id)
        val switches = SwitchDB.getAll(node._id)
        val beds = sensors.mapNotNull { sensor -> sensor.zone } + switches.mapNotNull { aSwitch -> aSwitch.zone }
        val mergeBeds = beds.toSet().toList()
        holder.subtitle?.text = mergeBeds.toString().replace(Regex("\\[|\\]"), "")

        holder.frame_bottom?.setOnClickListener {
            if(activity is MainActivity) {
                (activity as MainActivity).goToSwitches(node._id)
            }
        }

        getSensors(holder, node)
    }

    fun getSensors(holder: ViewHolder, node: Node) {
        /* TODO: get from Network when lost data */

        if(SensorDB.getAllFromNode(node._id).size <= 0) {
            SensorCall.getAllInFromNetwork(node._id, object : BloomCallback {
                override fun onSuccess(value: Any?, status: Int) {
                    if (value is List<*>) {
                        val response: List<Sensor> = value as List<Sensor>
                        SensorDB.add(response)
                        prepareSensors(holder, node, response)
                    }
                }

                override fun onServerError(status: Int) {
                    Toast.makeText(activity, "Error de red Sensor", Toast.LENGTH_LONG).show()
                }

                override fun onError(throwable: Throwable) {
                    throwable.printStackTrace()
                }
            })
        } else {
            val sensors = SensorDB.getAllFromNode(node._id)
            prepareSensors(holder, node, sensors)

        }

    }

    fun prepareSensors(holder: ViewHolder, node: Node, sensors: List<Sensor>) {

//        val adapter = SensorAdapter(activity, node.isCollapsed, sensors, false)
//        holder.sensorRecyclerView?.setHasFixedSize(true)
//        var grid = 2
//        if (node.isCollapsed) grid = 4
//        holder.sensorRecyclerView?.layoutManager = GridLayoutManager(activity, grid)
//        holder.sensorRecyclerView?.adapter = adapter
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView? = null
        var subtitle: TextView? = null
        var number: TextView? = null
        var sensorRecyclerView: RecyclerView? = null
        var frame: LinearLayout? = null
        var frame_top: LinearLayout? = null
        var frame_bottom: LinearLayout? = null

        init {
            this.name = itemView.findViewById(R.id.cell_node_name) as TextView
            this.subtitle = itemView.findViewById(R.id.cell_node_subtitle) as TextView
            this.number = itemView.findViewById(R.id.cell_node_number) as TextView
            this.sensorRecyclerView = itemView.findViewById(R.id.sensor_recyclerview)
            this.frame = itemView.findViewById(R.id.cell_node_frame) as LinearLayout
            this.frame_top = itemView.findViewById(R.id.cell_node_bg) as LinearLayout
            this.frame_bottom = itemView.findViewById(R.id.cell_node_title) as LinearLayout
        }
    }
}