package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.Place
import com.example.weatherapp.repository.PlaceRepository

class PlaceViewmodel() : ViewModel() {
    private val placeRepository = PlaceRepository()

    var placesList by mutableStateOf(listOf<Place>())

    var placeAutocompletionList by mutableStateOf(emptyList<String>())

    val selectedPlace : LiveData<Place>
        get()= _selectedPlace
    private val _selectedPlace : MutableLiveData<Place> = MutableLiveData(Place(placeName = "Hannover", creationDate = "23-05-2024", latitude = 52.3744779, longitude = 9.7385532))

    fun setSelectedPlace(place: Place) {
        _selectedPlace.value = place
    }

    suspend fun updateAutomcompletePlaceList(input : String){
        placeAutocompletionList = placeRepository.getAutocompletion(input)
    }

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

