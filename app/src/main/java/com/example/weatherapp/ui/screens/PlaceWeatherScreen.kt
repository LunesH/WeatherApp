package com.example.weatherapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
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
import com.example.weatherapp.ui.components.BackButton
import com.example.weatherapp.ui.components.DailyValuesCard
import com.example.weatherapp.ui.components.WeatherInfoImage
import com.example.weatherapp.ui.components.WeatherInfoTexts
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
    Scaffold(
        topBar = {
            Surface(shadowElevation = 16.dp) {
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
            weatherResource?.let { PlaceWeatherScreenContent(cityText, it) }
        }
    )
}
    @Composable
    fun PlaceWeatherScreenContent( cityText: String, weatherResource: Resource<WeatherData>){
        Column(
            modifier = Modifier
                .padding(top = 80.dp, start = 16.dp, end = 16.dp)
        ) {
            WeatherInfoTexts(cityText, weatherResource)
            WeatherInfoImage(weatherResource)
            DailyValuesCard()
        }
    }

