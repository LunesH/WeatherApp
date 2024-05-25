package com.example.weatherapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.R
import com.example.weatherapp.util.BackButton
import com.example.weatherapp.viewmodel.PlaceViewmodel


@OptIn(ExperimentalMaterial3Api::class)
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
        is Resource.Loading -> "Loading..."
        else -> "No info"
    }
    val weatherDescriptionText = when (weatherResource) {
        is Resource.Success -> weatherResource?.data?.weather?.get(0)?.weatherDescription
        is Resource.Loading -> "Loading..."
        else -> "No info"
    }
    val iconText = when (weatherResource) {
        is Resource.Success -> weatherResource?.data?.weather?.get(0)?.icon
        is Resource.Loading -> "Loading..."
        else -> "No info"
    }
    Scaffold(
        topBar = {
            Surface (shadowElevation = 16.dp) {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            color = Color.Black
                        )
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color(
                            0xffF5F5F5
                        )
                    ),
                    navigationIcon = {
                        BackButton(navController = navController)
                    }
                )
            }
        },
        content = { innerPadding ->
            Modifier.padding(innerPadding)

            Column(
                modifier = Modifier
                    .padding(top = 80.dp)
            ) {
                Text(
                    text = cityText ?: "None",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                )
                Text(
                    text = temperatureText.toString() ?: "No info",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                )
                Text(
                    text = weatherDescriptionText.toString() ?: "No info",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                )

                Text(
                    text = iconText.toString() ?: "No info",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                )
            }
        })

}