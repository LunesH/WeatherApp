package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class GeoCodingResponse(
    @SerializedName("name") var name: String,
    @SerializedName("local_names") var localNames: LocalNames,
    @SerializedName("lat") var lat: Double,
    @SerializedName("lon") var lon: Double,
    @SerializedName("country") var country: String,
    @SerializedName("state") var state: String,
)

data class LocalNames(
    val ja: String?,
    val lv: String?,
    val sq: String?,
    val oc: String?,
    val sc: String?,
    val uk: String?,
    val ka: String?,
    val fa: String?,
    val de: String?,
    val fy: String?,
    val ru: String?,
    val en: String?,
    val ko: String?,
    val lt: String?,
    val el: String?,
    val sk: String?,
    val cs: String?,
    val bg: String?,
    val fr: String?,
    val pl: String?,
    val it: String?,
    val ar: String?,
    val mk: String?,
    val be: String?,
    val zh: String?,
    val hu: String?,
    val la: String?,
    val ur: String?,
    val sr: String?,
    val he: String?
)