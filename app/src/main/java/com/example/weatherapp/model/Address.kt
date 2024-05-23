package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("label") var label: String,
    @SerializedName("countryCode") var countryCode: String,
    @SerializedName("countryName") var countryName : String,
    @SerializedName("stateCode") var stateCode : String,
    @SerializedName("state") var state : String,
    @SerializedName("countyCode") var countyCode: String,
    @SerializedName("county") var county : String,
    @SerializedName("city") var city : String,
    @SerializedName("postalCode") var postalCode: String,
)
