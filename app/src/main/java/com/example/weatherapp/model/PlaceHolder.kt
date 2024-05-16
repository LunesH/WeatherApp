package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

data class PlaceHolder(
    @SerializedName ("items") var items : List<AutocompletionPlace>,
)
