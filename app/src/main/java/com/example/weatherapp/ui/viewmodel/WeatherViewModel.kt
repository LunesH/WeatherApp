package com.example.weatherapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.util.Resource
import com.example.weatherapp.data.model.WeatherData
import com.example.weatherapp.data.model.DailyWeatherData
import com.example.weatherapp.data.model.HourlyWeatherData
import com.example.weatherapp.data.repository.WeatherRepository
import kotlinx.coroutines.launch

/**
 * This ViewModel receives weather data from the WeatherRepository. This ViewModel holds the weather
 * data as different LiveData Resources which can be observed by composable functions.
 * */
class WeatherViewModel(application : Application) : AndroidViewModel(application) {
    private val weatherRepository= WeatherRepository()
    private val apiKey = "efd4eb38672f855233e1f31b9c6c4d54"

    val weatherResource: LiveData<Resource<WeatherData>>
        get() = _weatherResource
    private val _weatherResource: MutableLiveData<Resource<WeatherData>> = MutableLiveData(Resource.Empty())

    val hourlyWeatherResource: LiveData<Resource<HourlyWeatherData>>
        get()= _hourlyWeatherResource
    private val _hourlyWeatherResource: MutableLiveData<Resource<HourlyWeatherData>> = MutableLiveData(
        Resource.Empty())

    val dailyWeatherResource: LiveData<Resource<DailyWeatherData>>
        get()=_dailyWeatherResource
    private val _dailyWeatherResource: MutableLiveData<Resource<DailyWeatherData>> = MutableLiveData(
        Resource.Empty())

    /**
     * Sets the current weather data
     * */
    fun setWeatherData(location: String){
        _weatherResource.value = Resource.Loading()
        viewModelScope.launch {
            _weatherResource.value = weatherRepository.getCurrentWeatherData(location, apiKey)
        }
    }

    /**
     * Sets the hourly weather data
     * */
    fun setHourlyWeatherData(location: String){
        _hourlyWeatherResource.value = Resource.Loading()
        viewModelScope.launch {
            _hourlyWeatherResource.value =weatherRepository.getHourlyWeatherData(location, apiKey)
        }
    }

    /**
     * Sets the daily weather data*/
    fun setDailyWeatherData(location: String){
        _dailyWeatherResource.value = Resource.Loading()
        viewModelScope.launch {
            _dailyWeatherResource.value =weatherRepository.getDailyWeatherData(location, apiKey)
        }
    }


}