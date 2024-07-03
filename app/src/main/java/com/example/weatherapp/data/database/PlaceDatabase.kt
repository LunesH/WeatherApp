package com.example.weatherapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.data.model.LocationEntity
import com.example.weatherapp.data.model.Place

/**
 * Local Database for storing users location and added places.
 * */
@Database(entities = [Place::class, LocationEntity::class], version = 6)
abstract class PlaceDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao
}