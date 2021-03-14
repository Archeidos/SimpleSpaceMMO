package com.codestallions.spacemmo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.codestallions.spacemmo.R
import com.codestallions.spacemmo.databinding.FragmentDestinationDialogBinding
import com.codestallions.spacemmo.databinding.FragmentLoginBinding
import com.codestallions.spacemmo.ui.viewmodel.DestinationViewModel
import com.codestallions.spacemmo.ui.viewmodel.DestinationViewModelFactory
import com.codestallions.spacemmo.ui.viewmodel.LoginViewModel

class DestinationDialogFragment private constructor() : DialogFragment() {
    private lateinit var binding: FragmentDestinationDialogBinding
    private val viewModel: DestinationViewModel by viewModels { DestinationViewModelFactory(arguments) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_destination_dialog, container, false)
        binding.destinationViewModel = viewModel
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(title: String?, location: String?): DestinationDialogFragment {
            val fragment = DestinationDialogFragment()
            val args = Bundle()
            args.putString("title", title)
            args.putString("location", location)
            fragment.arguments = args
            return fragment
        }
    }
}