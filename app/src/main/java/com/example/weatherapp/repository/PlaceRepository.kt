package com.example.weatherapp.repository

import android.util.Log
import com.example.weatherapp.model.AutocompletionPlace
import com.example.weatherapp.model.PlaceHolder
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

class PlaceRepository {
    val apiKey = "CAIdlznnUt41C80geKnq5GMQOXChOPHXe5x1vfY8NX0";

    suspend fun getAutocompletion(input: String): List<String> {
        var placeList = listOf<String>()
        withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://autocomplete.search.hereapi.com/v1/autocomplete?q="+input+"&apiKey="+apiKey)
                .get()
                .build()

            var response = client.newCall(request).execute();
            val responseBody = response.body()

            if (response.isSuccessful && responseBody != null) {
                val bodyString = responseBody.string()
                try {
                    val jsonObject = JsonParser.parseString(bodyString).asJsonObject
                    val gson = Gson()

                    val places = gson.fromJson(jsonObject, PlaceHolder::class.java)

                    for (place in places.items){
                        if (!placeList.contains(place.address.city)&&place.address.city!=null){
                            placeList += place.address.city
                        }
                    }
                }catch(e:Exception){
                    Log.e("exception",e.message.toString())
                }
            }
        }

           return placeList;


        }
    }
