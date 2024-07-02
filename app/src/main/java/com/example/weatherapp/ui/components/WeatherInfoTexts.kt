package com.example.weatherapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.weatherapp.util.Resource
import com.example.weatherapp.data.model.WeatherData

@Composable
fun WeatherInfoTexts(cityText: String, weatherResource: Resource<WeatherData>){
    val temperatureText = when (weatherResource) {
        is Resource.Success -> weatherResource.data?.weatherNumbers?.temperature?.toInt()
        is Resource.Loading -> "Loading..."
        else -> "No info"
    }
    val weatherDescriptionText = when (weatherResource) {
        is Resource.Success -> weatherResource.data?.weather?.get(0)?.weatherDescription
        is Resource.Loading -> "Loading..."
        else -> "No info"
    }
    val tempMin = when (weatherResource) {
        is Resource.Success -> weatherResource.data?.weatherNumbers?.tempMin?.toInt()
        is Resource.Loading -> "..."
        else -> "..."
    }
    val tempMax = when (weatherResource) {
        is Resource.Success -> weatherResource.data?.weatherNumbers?.tempMax?.toInt()
        is Resource.Loading -> "Loading..."
        else -> "No info"
    }
    Text(
        text = cityText ,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier
            .padding( horizontal = 16.dp)
    )
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = temperatureText.toString()+"°",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
        Text(
            text = weatherDescriptionText.toString(),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
    }
    Text(
        text = tempMin.toString()+"°" +"- "+ tempMax.toString()+"°",
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
    )
}

