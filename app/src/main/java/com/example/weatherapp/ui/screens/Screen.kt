package com.example.weatherapp.ui.screens

import androidx.annotation.StringRes
import com.example.weatherapp.R

/**
 * Class for handling Screens.
 */
sealed class Screen(
    val route: String,
    @StringRes val labelResourceId: Int
) {
    object AddPlaceScreen: Screen("add_place_screen", R.string.add_place)
    object PlaceWeatherScreen: Screen("place_weather_screen", R.string.place_weather)
}