package com.arech.bloom.app.zone

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arech.bloom.R
import com.arech.bloom.adapter.ZoneAdapter
import com.arech.bloom.app.zone.presenter.ZonePresenterImpl
import com.arech.bloom.app.main.MainActivity
import com.arech.bloom.app.zone.presenter.Constants
import com.arech.bloom.app.zone.presenter.mapper.PresenterZonesMapper
import com.arech.bloom.app.zone.presenter.model.PresenterZone
import com.arech.bloom.base.FragmentInterface
import com.arech.bloom.core.crud.SectorDB
import com.arech.bloom.event.NodeCenter
import com.arech.bloom.event.SensorCenter
import de.greenrobot.event.EventBus

class ZoneFragment: Fragment(), ZoneView, FragmentInterface {
    private var zonePresenter: ZonePresenterImpl? = null
    private var recyclerView: RecyclerView? = null
    private var sector: String? = null
    private var empty: LinearLayout? = null
    private val mapper: PresenterZonesMapper = PresenterZonesMapper()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val mySector = SectorDB.getForId(sector)

        val mainActivity = activity as? MainActivity
        mainActivity?.setupBar("Zonas del Sector " + mySector?.number, mySector?.crop ?: "", R.drawable.ic_arrow_back_white) { mainActivity.onBackPressed() }

        zonePresenter = ZonePresenterImpl(this)
        recyclerView = view?.findViewById(R.id.zone_recyclerview)
        empty = view?.findViewById(R.id.no_nodes)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bed, container, false)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
        recyclerView?.adapter = null
    }

    companion object {
        @JvmStatic
        fun newInstance(): ZoneFragment {
            val fragment = ZoneFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    fun setSector(id: String) {
        this.sector = id
    }

    override fun getZones(sectorId: String?, mapper: PresenterZonesMapper) {
        if (sectorId == null) {
            Toast.makeText(activity, "No reconozco el sector", Toast.LENGTH_LONG).show()
        } else {
            zonePresenter?.getZones(sectorId, mapper)
        }
    }

    override fun editZone(id: String, plantCount: Int?, crop: String?) {
        if (plantCount != null || crop != null) {
            zonePresenter?.editZone(id, plantCount, crop)
        }
    }

    override fun successEditZone(id: String, plantCount: Int?, crop: String?) {
        Toast.makeText(activity, "¡Tu zona ha sido actualizada!", Toast.LENGTH_SHORT).show()
        updateUI()
    }

    override fun showZones(zones: List<PresenterZone>) {
        if (zones.isEmpty()) {
            empty?.visibility = View.VISIBLE
        } else {
            empty?.visibility = View.GONE
            if(activity != null) {
                val adapter = ZoneAdapter(requireActivity(), this, zones)
                recyclerView?.setHasFixedSize(true)
                recyclerView?.layoutManager = LinearLayoutManager(activity)
                recyclerView?.adapter = adapter
            }
        }
    }

    override fun manageErrors(error: Int) {
        Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
    }

    override fun updateUI() {
        activity?.runOnUiThread {
            getZones(sector, mapper)
            recyclerView?.adapter?.notifyDataSetChanged()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.EDIT_ZONE_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                val zoneId = data?.getStringExtra(Constants.DATA_ZONE_ID)
                zoneId?.apply {
                    val plantCount = data.getIntExtra(Constants.DATA_PLANT_COUNT, 0)
                    val crop = data.getStringExtra(Constants.DATA_CROP)
                    editZone(this, plantCount, crop)
                }

            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(activity, "Cancelado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onEvent(event: NodeCenter.NodeEvent) {
        activity?.runOnUiThread {
           if (recyclerView?.adapter is ZoneAdapter) {
               (recyclerView!!.adapter as ZoneAdapter).onNodeEvent(event.zonesId, event.lastSync)
           }
        }
    }

    fun onEvent(event: SensorCenter.SensorEvent) {
        activity?.runOnUiThread {
            if (recyclerView?.adapter is ZoneAdapter) {
                (recyclerView!!.adapter as ZoneAdapter).onSensorEvent(event.event)
            }
        }
    }

}