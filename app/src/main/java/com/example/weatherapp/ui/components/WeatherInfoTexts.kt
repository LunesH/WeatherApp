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
import com.example.weatherapp.data.api.util.Resource
import com.example.weatherapp.data.model.WeatherData

@Composable
fun WeatherInfoTexts(cityText: String, weatherResource: Resource<WeatherData>){
    val temperatureText = when (weatherResource) {
        is Resource.Success -> weatherResource.data?.weatherNumbers?.temperature
        is Resource.Loading -> "Loading..."
        else -> "No info"
    }
    val weatherDescriptionText = when (weatherResource) {
        is Resource.Success -> weatherResource.data?.weather?.get(0)?.weatherDescription
        is Resource.Loading -> "Loading..."
        else -> "No info"
    }
    Text(
        text = cityText ,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
    )
    Row(verticalAlignment = Alignment.Bottom) {
        Text(
            text = temperatureText.toString(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
        )
        Text(
            text = weatherDescriptionText.toString(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 8.dp)
        )
    }
}
