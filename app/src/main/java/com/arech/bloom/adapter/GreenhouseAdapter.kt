package com.arech.bloom.adapter

import android.app.Activity
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
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arech.bloom.R
import com.arech.bloom.app.main.MainActivity
import com.arech.bloom.config.Config
import com.arech.bloom.core.crud.NodeDB
import com.arech.bloom.core.crud.SectorDB
import com.arech.bloom.core.crud.SensorDB
import com.arech.bloom.core.crud.UserPreferencesDB
import com.arech.bloom.models.Greenhouse
import com.arech.bloom.models.Sector
import com.arech.bloom.models.Sensor
import com.arech.bloom.models.Zone
import com.arech.bloom.network.call.SectorCall
import com.arech.bloom.network.call.SensorCall
import com.arech.bloom.network.callbacks.BloomCallback
import com.arech.bloom.network.res.BloomServerListResponse


/**
 * Created by Pili Arancibia on 8/18/19
 */

class GreenhouseAdapter(fragment: Fragment, activity: Activity, greenhouses: List<Greenhouse>): RecyclerView.Adapter<GreenhouseAdapter.ViewHolder>() {
    private val listdata: List<Greenhouse> = greenhouses.sortedBy { greenhouse -> greenhouse.name }
    private val activity = activity
    private val fragment = fragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.cell_greenhouse, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val greenhouse = listdata[position]
        viewBuild(holder, greenhouse)
        clickEvent(holder, greenhouse)
    }

    override fun getItemCount(): Int {
        return listdata.size
    }

    private fun clickEvent(holder: ViewHolder, greenhouse: Greenhouse) {
        /** Don't click in recyclerview **/
        holder.infoRecyclerView?.isLayoutFrozen = true

        holder.frame?.setOnClickListener {
            val mainActivity = activity as? MainActivity
            if (greenhouse.sectorCount != 1) mainActivity?.goToSectors(greenhouse._id)
            else {
                val sectors = SectorDB.getAll(greenhouse._id)
                if (sectors.isNotEmpty()) {
                    val sector = sectors.first()
                    sector?.apply {
                            mainActivity?.goToZones(_id, false)
                    }
                }
            }
        }
    }

    private fun viewBuild(holder: ViewHolder, greenhouse: Greenhouse) {
        if (greenhouse.sectorCount == 1) getSectorsFromNetwork(greenhouse)
        holder.name?.text = greenhouse.name
        holder.number?.text = activity.getString(R.string.greenhouse) + " " + greenhouse.number.toString()
        if (greenhouse.manager != null && !greenhouse.manager.isEmpty()) holder.manager?.text = greenhouse.manager
        val nodeCount = NodeDB.getCount(greenhouse._id)
        val userCount = greenhouse.users.size
        val sectorDisplay = if(greenhouse.sectorCount == 1) "sector" else "sectores"
        val nodeDisplay = if(nodeCount == 1) "nodo" else "nodos"
        val userDisplay = if(userCount == 1) "usuario" else "usuarios"

        val myDataList = arrayOf(
                Triple("m2", greenhouse.surface.toString(), R.drawable.ic_area),
                Triple("plantas", greenhouse.plantCount.toString(), R.drawable.ic_plants),
                Triple(sectorDisplay, greenhouse.sectorCount.toString(), R.drawable.ic_sectors),
                Triple(nodeDisplay, nodeCount.toString(), R.drawable.ic_nodes),
                Triple(userDisplay, userCount.toString(), R.drawable.ic_users),
                Triple("%", greenhouse.vitality.toString(), R.drawable.ic_vitality)
        )
        val adapter = GreenhouseDataAdapter(myDataList)
        holder.infoRecyclerView?.setHasFixedSize(true)
        holder.infoRecyclerView?.layoutManager = GridLayoutManager(activity, 3)
        holder.infoRecyclerView?.adapter = adapter

        redColorAlert(holder, greenhouse)
    }

    private fun redColorAlert(holder: ViewHolder, greenhouse: Greenhouse) {
        val unwrappedDrawable = AppCompatResources.getDrawable(activity, R.drawable.layout_rounded_top)
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)

        if(greenhouse.vitality < Config.VITALITY_CRITIC_THRESHOLD) DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(activity, R.color.critic_max_bg))
        else DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(activity, R.color.greenhouse_default_bg))

        holder.bg?.background = wrappedDrawable
    }

    private fun getSectorsFromNetwork(greenhouse: Greenhouse) {
        SectorCall.getAllInFromNetwork(greenhouse._id, object : BloomCallback {
            override fun onSuccess(value: Any?, status: Int) {
                val response: BloomServerListResponse = value as BloomServerListResponse
                SectorDB.add(response.data)
            }

            override fun onServerError(status: Int) {
                Toast.makeText(activity, R.string.net_error, Toast.LENGTH_LONG).show()
            }

            override fun onError(throwable: Throwable) {
                throwable.printStackTrace()
            }
        })
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView? = null
        var number: TextView? = null
        var infoRecyclerView: RecyclerView? = null
        var manager: TextView? = null
        var bg: LinearLayout? = null
        var frame: LinearLayout? = null

        init {
            this.name = itemView.findViewById(R.id.cell_greenhouse_name) as TextView
            this.number = itemView.findViewById(R.id.cell_greenhouse_number) as TextView
            this.infoRecyclerView = itemView.findViewById(R.id.gh_info_recyclerview)
            this.manager = itemView.findViewById(R.id.gh_info_manager) as TextView
            this.bg = itemView.findViewById(R.id.cell_greenhouse_bg) as LinearLayout
            this.frame = itemView.findViewById(R.id.cell_greenhouse_frame) as LinearLayout
        }
    }
}