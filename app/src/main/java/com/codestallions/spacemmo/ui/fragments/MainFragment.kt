package com.codestallions.spacemmo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.codestallions.spacemmo.R
import com.codestallions.spacemmo.SpaceMMO
import com.codestallions.spacemmo.databinding.FragmentMainBinding
import com.codestallions.spacemmo.model.PlayerModel
import com.codestallions.spacemmo.ui.activities.MainActivity
import com.codestallions.spacemmo.ui.viewmodel.MainViewModel

class MainFragment : BaseFragment() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainBinding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        mainViewModel = ViewModelProvider(this, NewInstanceFactory()).get(MainViewModel::class.java)
        mainBinding.mainViewModel = mainViewModel
        mainViewModel.syncPlayerData().observe(viewLifecycleOwner, Observer { playerModel: PlayerModel? ->
            if (playerModel != null) {
                SpaceMMO.getSession().cachePlayerData(playerModel)
            } else {
                val mainActivity = requireActivity() as MainActivity
                Toast.makeText(context, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show()
                mainActivity.navigateToLogin()
            }
        })
        return mainBinding.root
    }
}