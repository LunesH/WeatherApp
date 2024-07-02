package com.example.weatherapp.data.api

import com.example.weatherapp.data.model.DailyWeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface DailyWeatherApiService {
    @GET("daily?")
    suspend fun getCurrentWeatherData(
        @Query("q") location: String?,
        @Query("appid") apiKey: String?,
        @Query("count") numberOfDays: Int= 7,
        @Query("units") unit: String="metric"
    ): DailyWeatherData
}