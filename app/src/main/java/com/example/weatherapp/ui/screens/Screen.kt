package com.example.weatherapp.ui.screens

import androidx.annotation.StringRes
import com.example.weatherapp.R

sealed class Screen(
    val route: String,
    @StringRes val labelResourceId: Int
) {
    object AddPlaceScreen: Screen("add_place_screen", 1)
}