package com.example.weatherapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.weatherapp.data.api.util.Resource
import com.example.weatherapp.data.model.WeatherData
import com.example.weatherapp.viewmodel.WeatherViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.viewmodel.PlaceViewmodel


@Composable
fun PlaceWeatherScreen(navController: NavHostController,placeViewModel: PlaceViewmodel , weatherViewModel: WeatherViewModel = viewModel(), ) {
    val selectedPlace by placeViewModel.selectedPlace.observeAsState()
    val cityText = selectedPlace?.placeName ?: "No Place Selected"
    val weatherResource: Resource<WeatherData>? by weatherViewModel.weatherResource.observeAsState()

    LaunchedEffect(selectedPlace) {
        selectedPlace?.let {
            weatherViewModel.getWeatherData(it.placeName)
        }
    }

    val temperatureText = when (weatherResource) {
        is Resource.Success -> weatherResource?.data?.weatherNumbers?.temperature
        else -> "No info"
    }
    val weatherDescriptionText = when (weatherResource) {
        is Resource.Success -> weatherResource?.data?.weather?.get(0)?.weatherDescription
        else -> "No info"
    }
    val iconText = when (weatherResource) {
        is Resource.Success -> weatherResource?.data?.weather?.get(0)?.icon
        else -> "No info"
    }

    Column (
        modifier = Modifier
            .padding(top = 128.dp)
    ){
        Text(text = "Place Weather Screen")
        Button(onClick = {
            navController.navigate(Screen.AddPlaceScreen.route)
        },
            modifier = Modifier
                .width(150.dp)) {
        }
        Text(
            text = cityText?: "None",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)
        )
        Text(
            text = temperatureText.toString()?: "No info",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)
        )
        Text(
            text = weatherDescriptionText.toString()?: "No info",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)
        )
        Text(
            text = iconText.toString()?: "No info",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)
        )
    }

}