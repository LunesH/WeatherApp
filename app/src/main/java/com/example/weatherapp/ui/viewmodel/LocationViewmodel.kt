package com.example.weatherapp.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.model.Place
import com.example.weatherapp.data.repository.PlaceRepository

/**
 * Viewmodel to handle the location data of the user.
 */
class LocationViewmodel : ViewModel() {
    private val placeRepository = PlaceRepository()

    var userLocation = mutableStateOf(Place(placeName = "No Location", creationDate = "-", latitude = 0.0, longitude = 0.0))

    /**
     * Gets the name of the place to the coordinates of the place and returns a new place instance.
     */
    suspend fun getPlaceName(place: Place){
        try {
            var response = placeRepository.getPlaceName(place)
            userLocation.value = response
        }catch(e:Exception){
            Log.e("exception",e.message.toString())
        }
    }

}