package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * PlaceHolder data class to parse autocompletion response.
 * */
data class PlaceHolder(
    @SerializedName ("items") var items : List<AutocompletionPlace>,
)
