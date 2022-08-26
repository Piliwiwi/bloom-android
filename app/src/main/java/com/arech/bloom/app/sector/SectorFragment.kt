package com.arech.bloom.app.sector

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arech.bloom.R
import com.arech.bloom.adapter.SectorAdapter
import com.arech.bloom.app.main.MainActivity
import com.arech.bloom.app.sector.presenter.SectorPresenterImpl
import com.arech.bloom.base.FragmentInterface
import com.arech.bloom.core.crud.GreenhouseDB
import com.arech.bloom.event.SectorCenter
import com.arech.bloom.event.SensorCenter
import com.arech.bloom.models.Sector
import com.github.mikephil.charting.charts.PieChart
import de.greenrobot.event.EventBus

/**
 * Created by Pili Arancibia on 8/18/19
 */

class SectorFragment : Fragment(), SectorView, FragmentInterface {
    private var sectorPresenter: SectorPresenterImpl? = null
    private var recyclerView: RecyclerView? = null
    private var sectors = emptyList<Sector>()
    private var greenhouse: String? = null
    private var empty: LinearLayout? = null
    private var chart: PieChart? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val myGreenhouse = GreenhouseDB.getForId(greenhouse)

        val mainActivity = activity as? MainActivity
        mainActivity?.setupBar("Invernadero " + myGreenhouse?.number, myGreenhouse?.name, R.drawable.ic_arrow_back_white) { mainActivity.onBackPressed() }

        sectorPresenter = SectorPresenterImpl(this)
        recyclerView = view?.findViewById(R.id.sector_recyclerview)
        empty = view?.findViewById(R.id.no_sectors)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sector, container, false)
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
    }

    companion object {
        @JvmStatic
        fun newInstance(): SectorFragment {
            val fragment = SectorFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    fun setGreenhouse(id: String) {
        this.greenhouse = id
    }

    override fun getSectors(greenhouseId: String?) {
        if (greenhouseId == null) {
            Toast.makeText(activity, "No reconozco el invernadero", Toast.LENGTH_LONG).show()
        } else {
            sectorPresenter?.getSectors(greenhouseId)
        }
    }

    override fun showSectors(sectors: List<Sector>?) {
        if (sectors == null) {
            Toast.makeText(activity, "QUEEEEE", Toast.LENGTH_LONG).show()
        } else if (sectors.isEmpty()) {
            Toast.makeText(activity, R.string.no_sector, Toast.LENGTH_LONG).show()
            empty?.visibility = View.VISIBLE
        } else {
            empty?.visibility = View.GONE
            this.sectors = sectors
            if (activity != null) {
                val adapter = SectorAdapter(activity!!, sectors)
                recyclerView?.setHasFixedSize(true)
                recyclerView?.layoutManager = GridLayoutManager(activity, 2)
                recyclerView?.adapter = adapter
            }
        }
    }

    override fun manageErrors(error: Int) {
        Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
    }

    override fun updateUI() {
        activity?.runOnUiThread {
            getSectors(greenhouse)
            recyclerView?.adapter?.notifyDataSetChanged()
        }
    }

    fun onEvent(event: SectorCenter.SectorEvent) {
        activity?.runOnUiThread {
            getSectors(greenhouse)
        }
    }

    fun onEvent(event: SensorCenter.SensorEvent) {
        activity?.runOnUiThread {
            recyclerView?.adapter?.notifyDataSetChanged()
        }
    }

}