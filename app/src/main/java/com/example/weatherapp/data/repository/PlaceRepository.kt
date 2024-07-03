package com.example.weatherapp.data.repository

import android.util.Log
import com.example.weatherapp.data.model.AutocompletionPlace
import com.example.weatherapp.data.model.GeoCodingResponse
import com.example.weatherapp.data.model.Place
import com.example.weatherapp.data.model.PlaceHolder
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Repository that retrieves place data like place name, place autocompletion and place coordinates
 * */
class PlaceRepository {
    val apiKey = "CAIdlznnUt41C80geKnq5GMQOXChOPHXe5x1vfY8NX0";

    /**
     * Returns autocompletion of given input string using hereapi
     * */
    suspend fun getAutocompletion(input: String): List<String> {
        var placeList = listOf<String>()
        withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://autocomplete.search.hereapi.com/v1/autocomplete?q=" + input + "&apiKey=" + apiKey)
                .get()
                .build()

            var response = client.newCall(request).execute();
            val responseBody = response.body;

            if (response.isSuccessful && responseBody != null) {
                val bodyString = responseBody.string()
                try {
                    val jsonObject = JsonParser.parseString(bodyString).asJsonObject
                    val gson = Gson()

                    val places = gson.fromJson(jsonObject, PlaceHolder::class.java)

                    for (place in places.items) {
                        if (!placeList.contains(place.address.city) && place.address.city != null) {
                            placeList += place.address.city
                        }
                    }
                } catch (e: Exception) {
                    Log.e("exception", e.message.toString())
                }
            }
        }
        return placeList
    }


    /**
     * Returns coordinates in place instance of given place name of other place instance
     * using openweathermap geoCodingApi
     * */
    suspend fun getCoordinates(place: Place): Place {
        var geocodingApiKey = "efd4eb38672f855233e1f31b9c6c4d54"
        var newPlace = place
        withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("http://api.openweathermap.org/geo/1.0/direct?q=" + place.placeName + "&limit=1&appid=" + geocodingApiKey)
                .get()
                .build()

            var response = client.newCall(request).execute();
            val responseBody = response.body

            if (response.isSuccessful && responseBody != null) {
                val bodyString = responseBody.string()
                try {
                    val jsonArray = JsonParser.parseString(bodyString).asJsonArray
                    val jsonObject = jsonArray.get(0).asJsonObject
                    val gson = Gson()

                    val geoCodingResponse = gson.fromJson(jsonObject, GeoCodingResponse::class.java)
                    newPlace = Place(
                        placeName = place.placeName,
                        creationDate = place.creationDate,
                        latitude = geoCodingResponse.lat,
                        longitude = geoCodingResponse.lon
                    )

                } catch (e: Exception) {
                    Log.e("exception", e.message.toString())
                }
            }
        }

        return newPlace;


    }


    /**
     * Returns the place name in a place instance of the coordinates of a
     * given other place instance using reverse geoCodingApi of openweathermap.
     * */
    suspend fun getPlaceName(place: Place): Place {
        var geocodingApiKey = "efd4eb38672f855233e1f31b9c6c4d54"
        var newPlace = place
        Log.e("place", place.toString())
        withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("http://api.openweathermap.org/geo/1.0/reverse?lat=" + place.latitude + "&lon=" + place.longitude + "&limit=5&appid=" + geocodingApiKey)
                .get()
                .build()

            var response = client.newCall(request).execute();
            val responseBody = response.body

            val germanZoneId = ZoneId.of("Europe/Berlin")
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy kk:mm")
            val date = LocalDateTime
                .now(germanZoneId)
                .format(formatter)

            if (response.isSuccessful && responseBody != null) {
                val bodyString = responseBody.string()
                try {
                    val jsonArray = JsonParser.parseString(bodyString).asJsonArray
                    val jsonObject = jsonArray.get(0).asJsonObject
                    val gson = Gson()

                    val geoCodingResponse = gson.fromJson(jsonObject, GeoCodingResponse::class.java)
                    newPlace = Place(
                        placeName = geoCodingResponse.name,
                        creationDate = date,
                        latitude = geoCodingResponse.lat,
                        longitude = geoCodingResponse.lon
                    )

                    Log.e("response", newPlace.toString())
                } catch (e: Exception) {
                    Log.e("exception", e.message.toString())
                }
            }
        }

        return newPlace;


    }
}
