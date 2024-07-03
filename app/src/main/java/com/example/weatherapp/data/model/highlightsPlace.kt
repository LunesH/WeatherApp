package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * highlightsPlace data class for autocompletion.
 * */
data class highlightsPlace(
    @SerializedName("start") var start : Int,
    @SerializedName("end") var end: Int
)
