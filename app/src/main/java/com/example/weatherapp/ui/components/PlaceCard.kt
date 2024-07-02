package com.example.weatherapp.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.weatherapp.MainActivity
import com.example.weatherapp.data.model.Place
import com.example.weatherapp.ui.screens.Screen
import com.example.weatherapp.ui.viewmodel.PlaceViewmodel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PlaceCard(
    place: Place,
    navController: NavHostController,
    placeViewmodel: PlaceViewmodel
) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE4E4E4),
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),
        modifier = Modifier
            .padding(horizontal = 22.dp)
            .padding(vertical = 12.dp)
            .clickable {
                placeViewmodel.setSelectedPlace(place)
                navController.navigate(Screen.PlaceWeatherScreen.route)
                placeViewmodel.selectedPlace.value?.let { Log.e("set Place", it.placeName) }
            },
        shape = RoundedCornerShape(22.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(22.dp),
            ) {
                Row {
                    Text(
                        text = place.placeName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.Black,
                    )
                    if (place.creationDate == "location") {
                        Column(horizontalAlignment = Alignment.End) {
                            Icon(
                                Icons.Default.Place,
                                contentDescription = "Location",
                                modifier = Modifier.padding(top = 4.dp).size(26.dp)
                            )
                        }
                    }
                }
                Text(
                    text = place.creationDate,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    color = Color.Black,
                )
            }
            if (place.creationDate.equals("location")){
                Icon(
                    Icons.Default.Refresh,
                    contentDescription = "Location",
                    modifier = Modifier.padding(end = 22.dp)
                        .clickable {
                        })
            } else {
                Text(
                    text = "x",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(end = 28.dp)
                        .clickable {
                            placeViewmodel.placesList -= place
                            CoroutineScope(Dispatchers.IO).launch {
                                try {
                                    MainActivity.database.placeDao().delete(place)
                                } catch (e: Exception) {
                                    Log.e("Exception", "Error deleting place: ${e.message}", e)
                                }
                            }
                        }
                )
            }
        }
    }
}