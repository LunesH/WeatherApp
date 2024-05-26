package com.example.weatherapp.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.weatherapp.R

class IconMapper {

    /**
     * mapped die iconID von openweatherMap zu unseren eigenen Bildern. Nicht wundern, die iconIds sind bei
     * openweathermap nicht fortlaufend, es fehlen also keine in dieser List. Icons mit d : Day , n : Night.*/
    @Composable
    fun getIconResource(iconId: String): Painter {
        return when (iconId) {
            "01d" -> painterResource(id = R.drawable.sun)
            "01n" -> painterResource(id = R.drawable.clear_night)
            "02d" -> painterResource(id = R.drawable.few_clouds)
            "02n" -> painterResource(id = R.drawable.few_clouds_night)
            "03d" -> painterResource(id = R.drawable.cloudy)
            "03n" -> painterResource(id = R.drawable.few_clouds_night)
            "04d" -> painterResource(id = R.drawable.cloudy)
            "04n" -> painterResource(id = R.drawable.few_clouds_night)
            "09d" -> painterResource(id = R.drawable.shower_rain)
            "09n" -> painterResource(id = R.drawable.shower_rain)
            "10d" -> painterResource(id = R.drawable.rainy_day)
            "10n" -> painterResource(id = R.drawable.rainy_night)
            "11d" -> painterResource(id = R.drawable.thunderstorm)
            "11n" -> painterResource(id = R.drawable.thunderstorm)
            "13d" -> painterResource(id = R.drawable.snow)
            "13n" -> painterResource(id = R.drawable.snow)
            "50d" -> painterResource(id = R.drawable.mist)
            "50n" -> painterResource(id = R.drawable.mist)
            else -> painterResource(id = R.drawable.all_weather)
        }
    }
}