package com.arech.bloom.app.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.arech.bloom.R
import com.arech.bloom.app.main.MainActivity
import com.arech.bloom.app.main.presenter.MainDisplay
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.coroutines.*

/**
 * Created by Pili Arancibia on 8/15/19
 */

class HomeFragment: Fragment() {
    private var expandBtn: ImageButton? = null
    private var actionBtn: ImageButton? = null
    private var irrigation: Button? = null
    private var binnacle: Button? = null
    private var moreOptions: LinearLayout? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        expandBtn = view?.findViewById(R.id.main_expand_btn)
        actionBtn = view?.findViewById(R.id.btn_to_fields)
        irrigation = view?.findViewById(R.id.main_irrigation_btn)
        binnacle = view?.findViewById(R.id.main_binnacle_btn)
        moreOptions = view?.findViewById(R.id.main_more_options)

        val mainActivity = activity as? MainActivity
        mainActivity?.setupBar("Bloom", "", null) { MainDisplay.setNavigation(mainActivity) }

        setListeners()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.content_main, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    //Can do it more clean?
    private fun setListeners() {
        //Expand
        expandBtn?.setOnClickListener {
            if (moreOptions?.visibility == View.GONE) {
                expandBtn?.setImageResource(R.drawable.ic_expand_less)
                moreOptions?.visibility = View.VISIBLE
            } else {
                expandBtn?.setImageResource(R.drawable.ic_expand_more)
                moreOptions?.visibility = View.GONE
            }
        }

        //To Fields
        actionBtn?.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(activity, R.anim.bounce)
            actionBtn?.startAnimation(animation)
            CoroutineScope(Dispatchers.Default).launch {
                delay(timeMillis = animation.duration)
                activity?.runOnUiThread {
                    if (activity is MainActivity) {
                        val mainActivity = activity as MainActivity
                        mainActivity.goToGreenhouses()
                    }
                }
            }
        }

        //To Irrigation
        irrigation?.setOnClickListener {
            irrigation?.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.bounce))
            Toast.makeText(activity, "Proximamente...", Toast.LENGTH_SHORT).show()
        }

        //To Binnacle
        binnacle?.setOnClickListener {
            binnacle?.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.bounce))
            Toast.makeText(activity, "Proximamente...", Toast.LENGTH_SHORT).show()
        }
    }
}