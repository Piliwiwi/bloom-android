package com.arech.bloom.app.switches

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arech.bloom.R
import com.arech.bloom.adapter.SensorAdapter
import com.arech.bloom.adapter.SwitchAdapter
import com.arech.bloom.app.main.MainActivity
import com.arech.bloom.app.switches.presenter.SwitchPresenterImpl
import com.arech.bloom.app.zone.presenter.model.PresenterZone
import com.arech.bloom.base.FragmentInterface
import com.arech.bloom.core.crud.NodeDB
import com.arech.bloom.core.crud.UserDB
import com.arech.bloom.event.NodeCenter
import com.arech.bloom.event.SensorCenter
import com.arech.bloom.event.SwitchCenter
import com.arech.bloom.models.Node
import com.arech.bloom.models.Sensor
import com.arech.bloom.models.Switch
import com.arech.bloom.models.Zone
import com.arech.bloom.network.call.NodeCall
import com.arech.bloom.network.callbacks.BloomCallback
import com.arech.bloom.network.req.NodeModificationRequest
import de.greenrobot.event.EventBus
import java.text.SimpleDateFormat

/**
 * Created by Pili Arancibia on 1/11/20
 */

class SwitchFragment: Fragment(), SwitchView, FragmentInterface {
    private var switchPresenter: SwitchPresenterImpl? = null
    private var switch_recyclerView: RecyclerView? = null
    private var sensor_recyclerView: RecyclerView? = null
    private var nodeId: String? = null
    private var node: Node? = null
    private var zone: PresenterZone? = null
    private var switches = emptyList<Switch>()
    private var sensors = emptyList<Sensor>()
    private var empty: LinearLayout? = null
    private var dsDisplay: TextView? = null
    private var dsInput: EditText? = null
    private var dsBtn: ToggleButton?= null
    private var bar: LinearLayout? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val me = UserDB.getMe()

        switchPresenter = SwitchPresenterImpl(this)
        switch_recyclerView = view?.findViewById(R.id.switch_detail_recyclerview)
        sensor_recyclerView = view?.findViewById(R.id.sensor_detail_recyclerview)
        dsInput = view?.findViewById(R.id.admin_node_ds) as EditText
        dsBtn = view?.findViewById(R.id.admin_node_ds_btn) as ToggleButton
        dsDisplay = view?.findViewById(R.id.admin_node_ds_display) as TextView
        bar = view?.findViewById(R.id.fragment_switch_bar) as LinearLayout
        empty = view?.findViewById(R.id.no_nodes) //TODO

        if(nodeId != null) {
            val myNode = NodeDB.getForId(nodeId)
            node = myNode
            setTitle(nodeId)
            getSwitches(nodeId)
            getSensors(nodeId)
        } else {
            val mainActivity = activity as? MainActivity
            mainActivity?.setupBar(zone?.title, zone?.subtitle, R.drawable.ic_arrow_back_white) { mainActivity.onBackPressed() }
            getSensorsFromZone(zone?.id ?: "")
            getSwitchesFromZone(zone?.id ?: "")
        }

        //if(me != null && me.role == "admin") setAdminDsAccess()

    }

    private fun setAdminDsAccess() {
        dsInput?.visibility = View.VISIBLE
        dsBtn?.visibility = View.VISIBLE
        dsDisplay?.visibility = View.VISIBLE
        bar?.visibility = View.VISIBLE
        dsInput?.setText("" + node?.ds)

        dsInput?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
            override fun afterTextChanged(s: Editable) {
                dsBtn?.isChecked = !(s.toString().isEmpty() || s.toString() == "" + NodeDB.getForId(nodeId)?.ds)
                dsBtn?.isClickable = dsBtn?.isChecked ?: false
            }
        })

        dsBtn?.setOnClickListener {
            if(dsBtn?.isChecked == false && !dsInput?.text.isNullOrEmpty() && node != null) {
                val request = NodeModificationRequest(dsInput?.text?.toString()?.toLong() ?: node!!.ds ?: 900)
                NodeCall.setModification(nodeId, request, object : BloomCallback {
                    override fun onSuccess(value: Any?, status: Int) {
                        updateUI()
                        Toast.makeText(activity, "El tiempo de DeepSleep ha sido actualizado a " + dsInput?.text, Toast.LENGTH_SHORT).show()
                    }
                    override fun onServerError(status: Int) { }
                    override fun onError(throwable: Throwable) { }
                })
                (activity as MainActivity).dismissKeyboard()
            }
            dsBtn?.isClickable = false
        }

        dsBtn?.isClickable = false
    }

    private fun setTitle(nodeId: String?) {
        if(nodeId == null || nodeId.isEmpty()) return
        val myNode = NodeDB.getForId(nodeId)
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss a")
        val currentDate = sdf.format(myNode.lastSync)

        val mainActivity = activity as? MainActivity
        mainActivity?.setupBar("Nodo " + myNode?.number + ": " + (myNode.crop ?: "Sin nombre"), currentDate, R.drawable.ic_arrow_back_white) { mainActivity.onBackPressed() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_switch, container, false)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this);
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this);
    }

    companion object {
        @JvmStatic
        fun newInstance(): SwitchFragment {
            val fragment = SwitchFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    fun setNode(id: String) {
        this.nodeId = id
    }

    fun setZone(zone: PresenterZone) {
        this.zone = zone
    }

    override fun getSwitches(nodeId: String?) {
        if (nodeId == null) {
            Toast.makeText(activity, "No reconozco el nodo", Toast.LENGTH_LONG).show()
        } else {
            switchPresenter?.getSwitches(nodeId)
        }
    }

    override fun showSwitches(switches: List<Switch>?) {
        if (switches == null || switches.isEmpty()) {
            Toast.makeText(activity, "Esta zona no tiene controladores", Toast.LENGTH_LONG).show() //TODO
            empty?.visibility = View.VISIBLE
        } else {
            empty?.visibility = View.GONE
            this.switches = switches
            if(activity != null) {
                val adapter = SwitchAdapter(activity!!, switches)
                switch_recyclerView?.setHasFixedSize(true)
                switch_recyclerView?.layoutManager = LinearLayoutManager(activity)
                switch_recyclerView?.adapter = adapter
            }
        }
    }

    override fun getSensors(nodeId: String?) {
        if (nodeId == null) {
            Toast.makeText(activity, "No reconozco el nodo", Toast.LENGTH_LONG).show()
        } else {
            switchPresenter?.getSensors(nodeId)
        }
    }

    override fun showSensors(sensors: List<Sensor>?) {
//        if (sensors == null || sensors.isEmpty()) {
//            //Toast.makeText(activity, R.string.no_sector, Toast.LENGTH_LONG).show() //TODO
//            //empty?.visibility = View.VISIBLE
//        } else {
//            empty?.visibility = View.GONE
//            this.sensors = sensors
//            if(activity != null) {
//                val adapter = SensorAdapter(activity!!, node?.isCollapsed ?: zone!!.isCollapsed, sensors, true)
//                sensor_recyclerView?.setHasFixedSize(true)
//                sensor_recyclerView?.layoutManager = LinearLayoutManager(activity)
//                sensor_recyclerView?.adapter = adapter
//            }
//        }
    }

    override fun getSwitchesFromZone(zoneId: String) {
        if (zone == null) {
            Toast.makeText(activity, "No se reconoce la cama", Toast.LENGTH_LONG).show()
        } else {
            switchPresenter?.getSwitchesFromZone(zoneId)
        }
    }

    override fun getSensorsFromZone(zoneId: String) {
        if (zone == null) {
            Toast.makeText(activity, "No se reconoce la cama", Toast.LENGTH_LONG).show()
        } else {
            switchPresenter?.getSensorsFromZone(zoneId)
        }
    }

    override fun manageErrors(error: Int) {
        Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
    }

    override fun updateUI() {
        activity?.runOnUiThread {
            switch_recyclerView?.adapter?.notifyDataSetChanged()
            sensor_recyclerView?.adapter?.notifyDataSetChanged()
        }
    }

    fun onEvent(event: SwitchCenter.SwitchEvent) {
        activity?.runOnUiThread {
            switch_recyclerView?.adapter?.notifyDataSetChanged()
        }
    }

    fun onEvent(event: SensorCenter.SensorEvent) {
        activity?.runOnUiThread {
            if(this.node != null) setTitle(this.nodeId)
            sensor_recyclerView?.adapter?.notifyDataSetChanged()
        }
    }

    fun onEvent(event: NodeCenter.NodeEvent) {
        val myNode = NodeDB.getForId(nodeId)
        node = myNode
        activity?.runOnUiThread {
            setTitle(nodeId)
        }
    }

}