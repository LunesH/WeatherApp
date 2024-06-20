package com.example.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class LocationEntity(
    @PrimaryKey val id: Long = 0,
    val placeName : String,
    val creationDate: String,
    var latitude: Double,
    var longitude: Double
)
