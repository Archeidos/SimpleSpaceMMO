package com.codestallions.spacemmo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codestallions.spacemmo.R
import com.codestallions.spacemmo.SpaceMMO
import com.codestallions.spacemmo.databinding.FragmentSystemBinding
import com.codestallions.spacemmo.model.PlanetModel
import com.codestallions.spacemmo.ui.adapter.GeneralAdapter
import com.codestallions.spacemmo.ui.viewmodel.SystemViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [SystemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SystemFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private lateinit var systemViewModel: SystemViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments?.getString(ARG_PARAM1)
            mParam2 = arguments?.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val systemBinding: FragmentSystemBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_system, container, false)
        systemViewModel = ViewModelProvider(this, NewInstanceFactory()).get(SystemViewModel::class.java)
        systemBinding.systemViewModel = systemViewModel
        val systemRecycler: RecyclerView = systemBinding.root.findViewById(R.id.system_recycler_view)
        val generalAdapter = GeneralAdapter(context, viewLifecycleOwner, systemViewModel)
        systemRecycler.adapter = generalAdapter
        systemRecycler.layoutManager = LinearLayoutManager(context)
        systemViewModel.getLocalPlanetList(SpaceMMO.getSession().playerSystemLocation)
                .observe(viewLifecycleOwner, Observer { planets: List<PlanetModel?>? ->
                    generalAdapter.setPlanetList(planets)
                    generalAdapter.notifyDataSetChanged()
                    systemRecycler.viewTreeObserver.addOnPreDrawListener(
                            object : ViewTreeObserver.OnPreDrawListener {
                                override fun onPreDraw(): Boolean {
                                    systemRecycler.viewTreeObserver.removeOnPreDrawListener(this)
                                    for (i in 0 until systemRecycler.childCount) {
                                        val v = systemRecycler.getChildAt(i)
                                        v.alpha = 0.0f
                                        v.animate().alpha(1.0f)
                                                .setDuration(300)
                                                .setStartDelay(i * 50.toLong())
                                                .start()
                                    }
                                    return true
                                }
                            })
                })
        return systemBinding.root
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SystemFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): SystemFragment {
            val fragment = SystemFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}