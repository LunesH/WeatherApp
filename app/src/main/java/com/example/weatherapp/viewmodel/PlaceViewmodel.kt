package com.example.weatherapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.Place
import com.example.weatherapp.repository.PlaceRepository

class PlaceViewmodel : ViewModel() {
    private val placeRepository = PlaceRepository();

    var placesList by mutableStateOf(listOf<Place>(Place("Bremen","22-05-2024",0.0,0.0),Place("Hamburg","22-05-2024",0.0,0.0)))

    var placeAutocompletionList by mutableStateOf(emptyList<String>())
    suspend fun updateAutomcompletePlaceList(input : String){
        placeAutocompletionList = placeRepository.getAutocompletion(input)
    }
}

