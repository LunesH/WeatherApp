package com.example.weatherapp.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * This class is the connection point to get the hourly forecasts of the OpenWeatherMapAPI.
 * Its field hourlyWeatherClient is a implementation of the interface HourlyWeatherApiService.
 * */
class HourlyWeatherApi {
    /**
     * Companion object: The Kotlin compiler guarantees we will have one and only one instance of a companion object.
     * */
    companion object Factory {
        private val baseUrl = "https://pro.openweathermap.org/data/2.5/forecast/"
        val hourlyWeatherClient by lazy { createApi(baseUrl) }

        /**
         * Creates an instance of the HourlyWeatherApiService and configures the baseUrl,
         * an Http Client and the GsonConverter.
         * */
        private fun createApi(baseUrl: String): HourlyWeatherApiService {
            val client = OkHttpClient.Builder().apply {
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                readTimeout(10, TimeUnit.SECONDS)
                writeTimeout(10, TimeUnit.SECONDS)
            }.build()
            return Retrofit.Builder().baseUrl(baseUrl).client(client)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(HourlyWeatherApiService::class.java)
        }
    }
}