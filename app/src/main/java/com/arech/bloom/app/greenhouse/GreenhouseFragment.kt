package com.arech.bloom.app.greenhouse

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
import com.arech.bloom.adapter.GreenhouseAdapter
import com.arech.bloom.app.greenhouse.presenter.interfaces.GreenhousePresenter
import com.arech.bloom.app.greenhouse.presenter.GreenhousePresenterImpl
import com.arech.bloom.app.main.MainActivity
import com.arech.bloom.base.FragmentInterface
import com.arech.bloom.event.GreenhouseCenter
import com.arech.bloom.models.Greenhouse
import de.greenrobot.event.EventBus

/**
 * Created by Pili Arancibia on 8/18/19
 */

class GreenhouseFragment: Fragment(), GreenhouseView, FragmentInterface {
    private var greenhousePresenter: GreenhousePresenter? = null
    private var recyclerView: RecyclerView? = null
    private var infoRecyclerView: RecyclerView? = null
    private var empty: LinearLayout? = null
    private var greenhouses = emptyList<Greenhouse>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val mainActivity = activity as? MainActivity
        mainActivity?.setupBar("Invernaderos", "", R.drawable.ic_arrow_back_white) { mainActivity.onBackPressed() }

        greenhousePresenter = GreenhousePresenterImpl(this)
        recyclerView = view?.findViewById(R.id.greenhouse_recyclerview)
        infoRecyclerView = view?.findViewById(R.id.gh_info_recyclerview)
        empty = view?.findViewById(R.id.no_greenhouses)
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_greenhouse, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(): GreenhouseFragment {
            val fragment = GreenhouseFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getGreenhouses() {
        greenhousePresenter?.getGreenhouses()
    }

    override fun showGreenhouses(greenhouses: List<Greenhouse>?) {
        if (greenhouses == null || greenhouses.isEmpty()) {
            Toast.makeText(activity, R.string.no_greenhouse, Toast.LENGTH_LONG).show()
            empty?.visibility = View.VISIBLE
        } else {
            empty?.visibility = View.GONE
            this.greenhouses = greenhouses
            if(activity != null) {
                val adapter = GreenhouseAdapter(this, requireActivity(), greenhouses)
                recyclerView?.setHasFixedSize(true)
                recyclerView?.layoutManager = LinearLayoutManager(activity)
                recyclerView?.adapter = adapter
            }
        }
    }

    override fun manageErros(error: Int) {
        Toast.makeText(activity, error, Toast.LENGTH_LONG).show()
    }

    override fun updateUI() {
        activity?.runOnUiThread {
            getGreenhouses()
        }
    }

    /** for real time **/
    fun onEvent(event: GreenhouseCenter.GreenhouseEvent) {
        activity?.runOnUiThread {
            getGreenhouses()
        }
    }

}