package com.example.weatherapp.data.api

import com.example.weatherapp.data.model.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Defines a Retrofit API Endpoint for sending a get Request for the current weather forecasts
 * */
interface WeatherApiService {
    @GET("weather?")
    suspend fun getCurrentWeatherData(
        @Query("q") location: String?,
        @Query("appid") apiKey: String?,
        @Query("units") unit: String = "metric"
    ): WeatherData

}