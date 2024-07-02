package com.example.weatherapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherapp.util.Resource
import com.example.weatherapp.data.model.WeatherData
import com.example.weatherapp.util.IconMapper

@Composable
fun WeatherInfoImage(weatherResource: Resource<WeatherData>){
    val iconMapper = IconMapper();
    val iconID: String = when (weatherResource) {
        is Resource.Success -> weatherResource.data?.weather?.get(0)?.icon!!
        is Resource.Loading -> "Loading"
        else -> "No info"
    }
    val showImage = when (weatherResource) {
        is Resource.Success -> true
        else -> false
    }
    Box(modifier = Modifier
                    .heightIn(150.dp,150.dp)
                    .padding(top= 8.dp, bottom = 8.dp)){
        if (showImage) {
            Image(
                painter = iconMapper.getIconResource(iconId = iconID),
                contentDescription = "Weather Icon",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}