package com.example.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.api.util.Resource
import com.example.weatherapp.data.model.WeatherData
import com.example.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(application : Application) : AndroidViewModel(application) {
    private val weatherRepository= WeatherRepository()
    private val apiKey = "efd4eb38672f855233e1f31b9c6c4d54"

    val weatherResource: LiveData<Resource<WeatherData>>
        get() = _weatherResource
    private val _weatherResource: MutableLiveData<Resource<WeatherData>> = MutableLiveData(Resource.Empty())

    fun getWeatherData(location: String){
        _weatherResource.value = Resource.Loading()
        viewModelScope.launch {
            _weatherResource.value = weatherRepository.getCurrentWeatherData(location, apiKey)
        }
    }

}