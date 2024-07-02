package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class PlaceHolder(
    @SerializedName ("items") var items : List<AutocompletionPlace>,
)
