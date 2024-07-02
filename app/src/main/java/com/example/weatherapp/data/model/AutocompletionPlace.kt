package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class AutocompletionPlace (
    @SerializedName("title") var title: String,
    @SerializedName("id") var id : String,
    @SerializedName("language") var language : String,
    @SerializedName("resultType") var resultType: String,
    @SerializedName("localityType") var localityType: String,
    var address : Address,
    var highlights: placeHighlights,
)