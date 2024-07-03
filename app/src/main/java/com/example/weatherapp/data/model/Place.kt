package com.example.weatherapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Place data class for storing added places in local Roomdb.
 * */
@Entity(tableName = "places")
data class Place(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val placeName : String,
    val creationDate: String,
    var latitude: Double,
    var longitude: Double
)
