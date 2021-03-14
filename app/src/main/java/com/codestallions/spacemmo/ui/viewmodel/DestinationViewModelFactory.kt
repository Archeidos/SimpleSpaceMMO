package com.codestallions.spacemmo.ui.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DestinationViewModelFactory() : ViewModelProvider.NewInstanceFactory() {
    var title: String? = ""
    var locationInAU: String? = ""
    constructor(bundle: Bundle?) : this() {
        bundle?.let {
            title = bundle.getString("title")
            locationInAU = bundle.getString("location")
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DestinationViewModel(title ?: "", locationInAU ?: "") as T
    }
}