package com.example.weatherapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.weatherapp.data.model.LocationEntity
import com.example.weatherapp.data.model.Place

@Dao
interface PlaceDao {

    @Insert
    fun insertPlace(placeEntity: Place)

    @Insert
    fun insertLocation(location: LocationEntity)

    @Update
    fun updateLocation(location: LocationEntity)

    @Query("SELECT * FROM location")
    fun getAllLocations(): List<LocationEntity>

    @Update
    fun update(place: Place)

    @Delete
    fun delete(place: Place)

    @Query("SELECT * FROM places")
    fun getAllPlaces(): List<Place>

}

