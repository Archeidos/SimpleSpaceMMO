package com.codestallions.spacemmo.ui.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DestinationViewModelFactory() : ViewModelProvider.NewInstanceFactory() {
    var title: String? = null
    constructor(bundle: Bundle?) : this() {
        bundle?.let {
            title = bundle.getString("title")
        }
    }


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DestinationViewModel(title) as T
    }
}