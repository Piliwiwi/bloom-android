package com.arech.bloom.app.node

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arech.bloom.R
import com.arech.bloom.adapter.NodeAdapter
import com.arech.bloom.app.main.MainActivity
import com.arech.bloom.app.node.presenter.NodePresenterImpl
import com.arech.bloom.base.FragmentInterface
import com.arech.bloom.core.crud.SectorDB
import com.arech.bloom.core.crud.UserPreferencesDB
import com.arech.bloom.event.NodeCenter
import com.arech.bloom.event.SensorCenter
import com.arech.bloom.models.Node
import de.greenrobot.event.EventBus

/**
 * Created by Pili Arancibia on 9/28/19
 */

class NodeFragment: Fragment(), NodeView, FragmentInterface {
    private var nodePresenter: NodePresenterImpl? = null
    private var recyclerView: RecyclerView? = null
    private var sector: String? = null
    private var nodes = emptyList<Node>()
    private var empty: LinearLayout? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val mySector = SectorDB.getForId(sector)

        val mainActivity = activity as? MainActivity
        mainActivity?.setupBar("Nodos del Sector " + mySector?.number, mySector?.crop ?: "", R.drawable.ic_arrow_back_white) { mainActivity.onBackPressed() }

        nodePresenter = NodePresenterImpl(this)
        recyclerView = view?.findViewById(R.id.node_recyclerview)
        empty = view?.findViewById(R.id.no_nodes)

        getNodes(sector)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_node, container, false)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this);
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this);
    }

    companion object {
        @JvmStatic
        fun newInstance(): NodeFragment {
            val fragment = NodeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    fun setSector(id: String) {
        this.sector = id
    }

    override fun getNodes(sectorId: String?) {
        if (sectorId == null) {
            Toast.makeText(activity, "No reconozco el sector", Toast.LENGTH_LONG).show()
        } else {
            nodePresenter?.getNodes(sectorId)
        }
    }

    override fun showNodes(nodes: List<Node>?) {
        if (nodes == null || nodes.isEmpty()) {
            Toast.makeText(activity, R.string.no_sector, Toast.LENGTH_LONG).show()
            empty?.visibility = View.VISIBLE
        } else {
            empty?.visibility = View.GONE
            this.nodes = nodes
            if(activity != null) {
                val adapter = NodeAdapter(activity!!, nodes)
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
            getNodes(sector)
            recyclerView?.adapter?.notifyDataSetChanged()
        }
    }

    fun onEvent(event: NodeCenter.NodeEvent) {
        activity?.runOnUiThread {
            getNodes(sector)
        }
    }

    fun onEvent(event: SensorCenter.SensorEvent) {
        activity?.runOnUiThread {
            recyclerView?.adapter?.notifyDataSetChanged()
        }
    }
}