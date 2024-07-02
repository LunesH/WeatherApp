package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class DailyWeatherData(
    @SerializedName("city") val city: City,
    @SerializedName("list") val list: List<DataOfSpecificDay>
)


data class City(
    @SerializedName("name")val name: String
)

data class DataOfSpecificDay(
    @SerializedName("dt") val timeOfForecast: Int,
    @SerializedName("temp") val temperature: Temperature,
    @SerializedName("weather") val weather: List<Weather>
)

data class Temperature(
    @SerializedName("min")val tempMin: Double,
    @SerializedName("max")val tempMax: Double,
)
