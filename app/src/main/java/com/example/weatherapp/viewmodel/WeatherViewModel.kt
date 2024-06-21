package com.example.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.api.util.Resource
import com.example.weatherapp.data.model.WeatherData
import com.example.weatherapp.model.DailyWeatherData
import com.example.weatherapp.model.HourlyWeatherData
import com.example.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(application : Application) : AndroidViewModel(application) {
    private val weatherRepository= WeatherRepository()
    private val apiKey = "efd4eb38672f855233e1f31b9c6c4d54"

    val weatherResource: LiveData<Resource<WeatherData>>
        get() = _weatherResource
    private val _weatherResource: MutableLiveData<Resource<WeatherData>> = MutableLiveData(Resource.Empty())

    val hourlyWeatherResource: LiveData<Resource<HourlyWeatherData>>
        get()= _hourlyWeatherResource
    private val _hourlyWeatherResource: MutableLiveData<Resource<HourlyWeatherData>> = MutableLiveData(Resource.Empty())

    val dailyWeatherResource: LiveData<Resource<DailyWeatherData>>
        get()=_dailyWeatherResource
    private val _dailyWeatherResource: MutableLiveData<Resource<DailyWeatherData>> = MutableLiveData(Resource.Empty())

    fun getWeatherData(location: String){
        _weatherResource.value = Resource.Loading()
        viewModelScope.launch {
            _weatherResource.value = weatherRepository.getCurrentWeatherData(location, apiKey)
        }
    }

    fun getHourlyWeatherData(location: String){
        _hourlyWeatherResource.value =Resource.Loading()
        viewModelScope.launch {
            _hourlyWeatherResource.value =weatherRepository.getHourlyWeatherData(location, apiKey)
        }
    }

    fun getDailyWeatherData(location: String){
        _dailyWeatherResource.value =Resource.Loading()
        viewModelScope.launch {
            _dailyWeatherResource.value =weatherRepository.getDailyWeatherData(location, apiKey)
        }
    }


}