package com.example.weatherapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.data.model.LocationEntity
import com.example.weatherapp.data.model.Place

@Database(entities = [Place::class, LocationEntity::class], version = 6)
abstract class PlaceDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao
}