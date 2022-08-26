package com.arech.bloom.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.arech.bloom.R
import com.arech.bloom.app.main.MainActivity
import com.arech.bloom.config.Resources
import com.arech.bloom.core.crud.UserPreferencesDB
import com.arech.bloom.models.Sector
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

/**
 * Created by Pili Arancibia on 9/27/19
 */

class SectorAdapter(context: Context, sectors: List<Sector>) : RecyclerView.Adapter<SectorAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.cell_sector, parent, false)
        return ViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return listdata.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sector = listdata[position]
        holder.chart?.data = null
        holder.chart?.description?.isEnabled = false

        // radius of the center hole in percent of maximum radius
        holder.chart?.holeRadius = 57f
        holder.chart?.centerText = sector.zones.toString()
        holder.chart?.setHoleColor(activity.resources.getColor(R.color.bloom_bg))
        holder.chart?.setEntryLabelColor(Color.WHITE)
        holder.chart?.setCenterTextColor(Color.WHITE)
        holder.chart?.setCenterTextSize(26f)
        holder.chart?.transparentCircleRadius = 0f
        holder.chart?.legend?.isEnabled = false
        holder.chart?.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        holder.chart?.renderer?.paintRender?.setShadowLayer(3f, 3f, 3f, activity.resources.getColor(R.color.shadow))
        holder.chart?.isClickable = false
        holder.chart?.isFocusable = false
        holder.chart?.setTouchEnabled(false)

        holder.chart?.data = generatePieData(sector)

        val highl = sector.sensorsInfo.highl
        holder.chart?.highlightValue(highl, 0)

        viewBuild(holder, sector)
        clickEvent(holder, sector)

    }

    private fun viewBuild(holder: ViewHolder, sector: Sector) {
        holder.name?.text = sector.crop
        holder.number?.text = activity.getString(R.string.sector) + " " + sector.number.toString()

        val unwrappedDrawable = AppCompatResources.getDrawable(activity, R.drawable.layout_rounded_top)
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
        DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(activity, R.color.greenhouse_default_bg))
    }

    private fun clickEvent(holder: ViewHolder, sector: Sector) {
        holder.chart?.setOnClickListener {
            (activity as? MainActivity)?.goToZones(sector._id, false)
        }

        holder.frame?.setOnClickListener {
            (activity as? MainActivity)?.goToZones(sector._id, false)
        }
    }

    private fun generatePieData(sector: Sector): PieData {

        val entries = ArrayList<PieEntry>()

        entries.add(PieEntry(sector.sensorsInfo.criticMaxCount,
                if (sector.sensorsInfo.criticMaxCount > 0) sector.sensorsInfo.criticMaxCount.toString() else ""))
        entries.add(PieEntry(sector.sensorsInfo.warningMinCount,
                if (sector.sensorsInfo.warningMinCount > 0) sector.sensorsInfo.warningMinCount.toString() else ""))
        entries.add(PieEntry(sector.sensorsInfo.greenCount,
                if (sector.sensorsInfo.greenCount > 0) sector.sensorsInfo.greenCount.toString() else ""))
        entries.add(PieEntry(sector.sensorsInfo.warningMaxCount,
                if (sector.sensorsInfo.warningMaxCount > 0) sector.sensorsInfo.warningMaxCount.toString() else ""))
        entries.add(PieEntry(sector.sensorsInfo.criticMinCount,
                if (sector.sensorsInfo.criticMinCount > 0) sector.sensorsInfo.criticMinCount.toString() else ""))
        entries.add(PieEntry(sector.sensorsInfo.inactiveCount,
                if (sector.sensorsInfo.inactiveCount > 0) sector.sensorsInfo.inactiveCount.toString() else ""))

        val ds1 = PieDataSet(entries, "Zonas")
        ds1.colors = Resources.SOFT_TRAFFIC_COLORS.toList()
        ds1.sliceSpace = 6f
        ds1.setDrawValues(false)
        return PieData(ds1)
    }

    private var listdata: List<Sector> = sectors
    private var activity = context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView? = null
        var number: TextView? = null
        var bg: LinearLayout? = null
        var frame: LinearLayout? = null
        var chart: PieChart? = null

        init {
            this.name = itemView.findViewById(R.id.cell_sector_name) as TextView
            this.number = itemView.findViewById(R.id.cell_sector_number) as TextView
            this.bg = itemView.findViewById(R.id.cell_sector_bg) as LinearLayout
            this.frame = itemView.findViewById(R.id.cell_sector_frame) as LinearLayout
            this.chart = itemView.findViewById(R.id.sector_pieChart) as PieChart
        }
    }
}