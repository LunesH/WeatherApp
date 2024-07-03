package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data classes to hold the weather data from the OpenWeatherMapApi
 * */
data class WeatherData(
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("main") val weatherNumbers: MainInfo,
    @SerializedName("wind") val wind: Wind,
)

data class Weather(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val keyword: String,
    @SerializedName("description") val weatherDescription: String,
    @SerializedName("icon") val icon: String
)

data class MainInfo(
    @SerializedName("temp") val temperature: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("humidity") val humidity: Int
)

data class Wind(@SerializedName("speed") val speed: Double)