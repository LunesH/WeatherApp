package com.example.weatherapp.data.api

import com.example.weatherapp.data.model.HourlyWeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface HourlyWeatherApiService {
    @GET("hourly?")
    suspend fun getHourlyWeatherData(
        @Query("q") location: String?,
        @Query("appid") apiKey: String?,
        @Query("count") numberOfHours: Int= 24,
        @Query("units") unit: String="metric"
    ): HourlyWeatherData
}