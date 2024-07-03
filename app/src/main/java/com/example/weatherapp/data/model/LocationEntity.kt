package com.example.weatherapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * LocationEntity data class for storing the users location in local RoomDb.
 * */
@Entity(tableName = "location")
data class LocationEntity(
    @PrimaryKey val id: Long = 0,
    val placeName : String,
    val creationDate: String,
    var latitude: Double,
    var longitude: Double
)
