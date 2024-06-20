package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.Place
import com.example.weatherapp.repository.PlaceRepository

class LocationViewmodel : ViewModel() {
    private val placeRepository = PlaceRepository()

    var userLocation = mutableStateOf(Place(placeName = "No Location", creationDate = "-", latitude = 0.0, longitude = 0.0))

    suspend fun getPlaceName(place: Place){
        try {
            var response = placeRepository.getPlaceName(place)
            userLocation.value = response
        }catch(e:Exception){
            Log.e("exception",e.message.toString())
        }
    }

}