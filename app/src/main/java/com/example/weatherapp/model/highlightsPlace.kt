package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

data class highlightsPlace(
    @SerializedName("start") var start : Int,
    @SerializedName("end") var end: Int
)
