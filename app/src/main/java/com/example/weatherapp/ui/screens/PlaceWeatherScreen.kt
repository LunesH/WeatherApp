package com.example.weatherapp.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.weatherapp.R
import com.example.weatherapp.data.model.DailyWeatherData
import com.example.weatherapp.data.model.HourlyWeatherData
import com.example.weatherapp.data.model.WeatherData
import com.example.weatherapp.ui.components.BackButton
import com.example.weatherapp.ui.components.DailyValuesCard
import com.example.weatherapp.ui.components.ExtraWeatherInfo
import com.example.weatherapp.ui.components.HourlyValuesCard
import com.example.weatherapp.ui.components.WeatherInfoImage
import com.example.weatherapp.ui.components.WeatherInfoTexts
import com.example.weatherapp.ui.viewmodel.PlaceViewmodel
import com.example.weatherapp.ui.viewmodel.WeatherViewModel
import com.example.weatherapp.util.Resource

/**
 * This is the screen where the weather data for a specific place is shown.
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceWeatherScreen(
    navController: NavHostController,
    placeViewModel: PlaceViewmodel,
    weatherViewModel: WeatherViewModel = viewModel(),
) {
    val selectedPlace by placeViewModel.selectedPlace.observeAsState()
    val cityText = selectedPlace?.placeName ?: "No Place Selected"
    val weatherResource: Resource<WeatherData>? by weatherViewModel.weatherResource.observeAsState()
    val hourlyWeatherResource: Resource<HourlyWeatherData>? by weatherViewModel.hourlyWeatherResource.observeAsState()
    val dailyWeatherResource: Resource<DailyWeatherData>? by weatherViewModel.dailyWeatherResource.observeAsState()
    LaunchedEffect(selectedPlace) {
        selectedPlace?.let {
            weatherViewModel.setWeatherData(it.placeName)
            weatherViewModel.setHourlyWeatherData(it.placeName)
            weatherViewModel.setDailyWeatherData(it.placeName)
        }
    }
    Scaffold(topBar = {
        Surface(shadowElevation = 16.dp) {
            TopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.app_name), color = Color.Black
                )
            }, colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color(
                    0xffF5F5F5
                )
            ), navigationIcon = {
                BackButton(navController = navController)
            })
        }
    }, content = { innerPadding ->
        Modifier.padding(innerPadding)
        weatherResource?.let {
            hourlyWeatherResource?.let { it1 ->
                dailyWeatherResource?.let { it2 ->
                    PlaceWeatherScreenContent(
                        cityText, it, it1, it2
                    )
                }
            }
        }
    })
}

/**
 * The content of the PlaceWeatherScreen including information texts, a large icon, hourly data,
 * daily data and additional information.
 * */
@Composable
fun PlaceWeatherScreenContent(
    cityText: String,
    weatherResource: Resource<WeatherData>,
    hourlyWeatherResource: Resource<HourlyWeatherData>,
    dailyWeatherResource: Resource<DailyWeatherData>
) {
    LazyColumn(
        modifier = Modifier
            .padding(top = 80.dp, start = 16.dp, end = 16.dp)
            .size(800.dp)
    ) {
        item { WeatherInfoTexts(cityText, weatherResource) }
        item { WeatherInfoImage(weatherResource) }
        item { HourlyValuesCard(hourlyWeatherResource) }
        item { DailyValuesCard(dailyWeatherResource) }
        item { ExtraWeatherInfo(weatherResource) }
        item { Spacer(modifier = Modifier.height(8.dp)) }
    }
}

