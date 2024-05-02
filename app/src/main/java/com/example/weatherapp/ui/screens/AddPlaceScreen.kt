package com.example.weatherapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AddPlaceScreen(navController: NavHostController) {
    Column (
        modifier = Modifier
            .padding(top = 128.dp)
    ){
        Text(text = "Add Place Screen")
        Button(onClick = {
            navController.navigate(Screen.PlaceWeatherScreen.route)
        },
            modifier = Modifier
                .width(150.dp)
        )
        {

        }
    }
}