package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data classes to hold the hourly weather data from the OpenWeatherMapApi
 * */
data class HourlyWeatherData(
    @SerializedName("list")val listOfDataPerHour : List<DataOfSpecificHour>
)

data class DataOfSpecificHour(
    @SerializedName("main")val weatherNumbers: MainInfo,
    @SerializedName("weather")val weather : List<Weather>,
    @SerializedName("dt_txt")val time: String,

    )

