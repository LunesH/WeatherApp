package com.example.weatherapp.data.repository

import android.util.Log
import com.example.weatherapp.data.api.DailyWeatherApi
import com.example.weatherapp.data.api.DailyWeatherApiService
import com.example.weatherapp.data.api.HourlyWeatherApi
import com.example.weatherapp.data.api.HourlyWeatherApiService
import com.example.weatherapp.data.api.WeatherApi
import com.example.weatherapp.data.api.WeatherApiService
import com.example.weatherapp.util.Resource
import com.example.weatherapp.data.model.WeatherData
import com.example.weatherapp.data.model.DailyWeatherData
import com.example.weatherapp.data.model.HourlyWeatherData
import kotlinx.coroutines.withTimeout


class WeatherRepository {
    private val weatherService: WeatherApiService = WeatherApi.weatherClient
    private val hourlyWeatherService: HourlyWeatherApiService = HourlyWeatherApi.hourlyWeatherClient
    private val dailyWeatherService: DailyWeatherApiService = DailyWeatherApi.dailyWeatherClient

    suspend fun getDailyWeatherData(location: String, apiKey: String): Resource<DailyWeatherData> {
        val response = try {
            withTimeout(60_000) {
                dailyWeatherService.getCurrentWeatherData(location, apiKey)
            }
        } catch(e: Exception) {
            Log.e("WeatherRepository", e.message ?: "No exception message available")
            return Resource.Error("An unknown error occurred")
        }
        return Resource.Success(response)
    }

    suspend fun getCurrentWeatherData(location: String, apiKey: String): Resource<WeatherData> {
        val response = try {
            withTimeout(60_000) {
                weatherService.getCurrentWeatherData(location, apiKey)
            }
        } catch(e: Exception) {
            Log.e("WeatherRepository", e.message ?: "No exception message available")
            return Resource.Error("An unknown error occurred")
        }
        return Resource.Success(response)
    }

    suspend fun getHourlyWeatherData(location: String, apiKey: String): Resource<HourlyWeatherData> {
        val response = try {
            withTimeout(60_000) {
                hourlyWeatherService.getHourlyWeatherData(location, apiKey)
            }
        } catch(e: Exception) {
            Log.e("WeatherRepository", e.message ?: "No exception message available")
            return Resource.Error("An unknown error occurred")
        }
        return Resource.Success(response)
    }
}
