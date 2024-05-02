package com.example.weatherapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.weatherapp.model.Place
import com.example.weatherapp.util.PlaceCard

@Composable
fun AddPlaceScreen(navController: NavHostController) {
    Column (
        modifier = Modifier
            .fillMaxHeight()
            .background(Color(0xffD2D2D2))
    ){
        Spacer(modifier = Modifier.height(64.dp))
        Text(text = "Add Place Screen")
        Button(onClick = {
            navController.navigate(Screen.PlaceWeatherScreen.route)
        },
            modifier = Modifier
                .width(150.dp)
        )
        {

        }
        PlaceCard(Place("Bremen","01-05-2024",0.0,0.0),navController)
        PlaceCard(Place("Hamburg","02-05-2024",0.0,0.0),navController)

    }
}