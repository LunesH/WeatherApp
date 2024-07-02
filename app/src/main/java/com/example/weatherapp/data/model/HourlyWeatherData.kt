package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class HourlyWeatherData(
    @SerializedName("cod")val cod : String,
    @SerializedName("message") val message : Int,
    @SerializedName("cnt")val count : Int,
    @SerializedName("list")val listOfDataPerHour : List<DataOfSpecificHour>
)

data class DataOfSpecificHour(
    @SerializedName("dt")val dt: Int,
    @SerializedName("main")val weatherNumbers: MainInfo,
    @SerializedName("weather")val weather : List<Weather>,
    @SerializedName("clouds")val clouds: Clouds,
    @SerializedName("wind")val wind: Wind,
    @SerializedName("dt_txt")val time: String,

    )

