package com.example.weatherapp.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.model.Place
import com.example.weatherapp.data.repository.PlaceRepository

/**
 * Viewmodel to handle place data like all added places, the autocompletion
 * of places and the currently selected place.
 */
class PlaceViewmodel() : ViewModel() {
    private val placeRepository = PlaceRepository()

    var placesList by mutableStateOf(listOf<Place>())

    var placeAutocompletionList by mutableStateOf(emptyList<String>())

    val selectedPlace : LiveData<Place>
        get()= _selectedPlace
    private val _selectedPlace : MutableLiveData<Place> = MutableLiveData(Place(placeName = "", creationDate = "", latitude = 52.3744779, longitude = 9.7385532))

    /**
     * Sets the current selected place
     * */
    fun setSelectedPlace(place: Place) {
        _selectedPlace.value = place
    }

    /**
     * Calls the autocompletion of place search
     * */
    suspend fun updateAutomcompletePlaceList(input : String){
        placeAutocompletionList = placeRepository.getAutocompletion(input)
    }

    /**
     * Gets the coordinates of the place and returns a new place Instance
     * */
    suspend fun updatePlaceCoordinates(place: Place){
        try {
            var newPlace = placeRepository.getCoordinates(place)
            val index = placesList.indexOfFirst { newPlace.placeName == it.placeName }
            placesList[index].longitude = newPlace.longitude
            placesList[index].latitude = newPlace.latitude
        }catch(e:Exception){
            Log.e("exception",e.message.toString())
        }
    }
}

