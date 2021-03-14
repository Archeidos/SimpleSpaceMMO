package com.codestallions.spacemmo.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.codestallions.spacemmo.SpaceMMO
import kotlin.math.pow
import kotlin.math.sqrt

class DestinationViewModel(val title: String, private val locationInAU: String) : ViewModel() {

    fun getDistanceInAU(): String {
        val destinationPair = locationInAU.split(",")
        val currentLocationPair = SpaceMMO.getSession().playerCoordinates.split(",")
        val x = destinationPair[0].toDouble()
        val y = destinationPair[1].toDouble()
        val x2 = currentLocationPair[0].toDouble()
        val y2 = currentLocationPair[1].toDouble()
        val distance = sqrt((x2 - x).pow(2) + (y2 - y).pow(2))
        return "Distance: ${String.format("%.2f", distance)} AU"
    }

}


