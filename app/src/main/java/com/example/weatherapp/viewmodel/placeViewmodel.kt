package com.example.weatherapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.repository.PlaceRepository

class PlaceViewmodel : ViewModel() {
    private val placeRepository = PlaceRepository();


    var placeAutocompletionList by mutableStateOf(emptyList<String>())
    suspend fun updateAutomcompletePlaceList(input : String){
        placeAutocompletionList = placeRepository.getAutocompletion(input)
    }
}

