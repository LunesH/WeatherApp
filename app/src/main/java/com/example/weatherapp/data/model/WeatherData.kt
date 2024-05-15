package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class WeatherData (
    @SerializedName("coord")val coordinates : Coordinates,
    @SerializedName("weather")val weather : List<Weather>,
    @SerializedName("base")val base : String,
    @SerializedName("main")val weatherNumbers: MainInfo,
    @SerializedName("visibility")val visibility: String,
    @SerializedName("wind")val wind: Wind,
    @SerializedName("clouds")val clouds: Clouds,
    @SerializedName("dt")val dt: Int,
    @SerializedName("sys")val system: System,
    @SerializedName("timezone")val timezone: Int,
    @SerializedName("id")val id: Int,
    @SerializedName("name")val name: String,
    @SerializedName("cod")val cod: Int,
)

    data class Coordinates(@SerializedName("lon")val lon: Double,
                       @SerializedName("lat")val lat: Double)

    data class Weather(@SerializedName("id")val id: Int,
                   @SerializedName("main")val keyword : String,
                   @SerializedName("description")val weatherDescription: String,
                   @SerializedName("icon")val icon: String)

    data class MainInfo(@SerializedName("temp")val temperature: Double,
                        @SerializedName("feels_like")val feelsLike: Double,
                        @SerializedName("temp_min")val tempMin: Double,
                        @SerializedName("temp_max")val tempMax: Double,
                        @SerializedName("pressure")val pressure: Int,
                        @SerializedName("humidity")val humidity: Int)

    data class Wind(@SerializedName("speed")val speed: Double,
                   @SerializedName("deg")val degree : Int)

    data class Clouds(@SerializedName("all")val clouds: Int)

    data class System(@SerializedName("type")val type: Int,
                    @SerializedName("id")val id: Int,
                    @SerializedName("country")val country: String,
                    @SerializedName("sunrise")val sunrise: Int,
                    @SerializedName("sunset")val sunset: Int)