package com.example.weatherapp.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HourlyWeatherApi {
    companion object Factory{
        private val baseUrl = "https://pro.openweathermap.org/data/2.5/forecast/"
        val hourlyWeatherClient by lazy { createApi(baseUrl) }

        private fun createApi(baseUrl: String): HourlyWeatherApiService {
            val client = OkHttpClient.Builder().apply {
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                readTimeout(10, TimeUnit.SECONDS)
                writeTimeout(10, TimeUnit.SECONDS)
            }.build()
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HourlyWeatherApiService::class.java)
        }
    }
}