package com.example.weatherapp.data.api

import com.example.weatherapp.data.model.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApiService {
    @GET("weather?")
    suspend fun getCurrentWeatherData(
        @Query("q") location: String?,
        @Query("appid") apiKey: String?,
        @Query("units") unit: String="metric"
    ): WeatherData

}