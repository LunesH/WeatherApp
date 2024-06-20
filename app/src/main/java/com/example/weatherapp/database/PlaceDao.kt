package com.example.weatherapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Index
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import com.example.weatherapp.model.Place

@Dao
interface PlaceDao {

    @Insert
    fun insert(placeEntity: Place)

    @Update
    fun update(place: Place)

    @Delete
    fun delete(place: Place)

    @Query("SELECT * FROM places")
    fun getAllPlaces(): List<Place>

}

