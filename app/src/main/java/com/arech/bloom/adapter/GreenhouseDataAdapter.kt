package com.arech.bloom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arech.bloom.R

/**
 * Created by Pili Arancibia on 8/18/19
 */

class GreenhouseDataAdapter(greenhouseDataList: Array<Triple<String, String, Int>>): RecyclerView.Adapter<GreenhouseDataAdapter.ViewHolder>() {
    private var data = greenhouseDataList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.cell_greenhouse_info, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.icon?.setImageResource(data[position].third)
        holder.value?.text = data[position].second
        holder.unit?.text = data[position].first
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var value: TextView? = null
        var unit: TextView? = null
        var icon: ImageView? = null

        init {
            this.value = itemView.findViewById(R.id.gh_info_data) as TextView
            this.unit = itemView.findViewById(R.id.gh_info_unit) as TextView
            this.icon = itemView.findViewById(R.id.gh_info_icon) as ImageView
        }
    }
}