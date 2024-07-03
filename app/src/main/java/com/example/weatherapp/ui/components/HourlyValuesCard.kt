package com.example.weatherapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.weatherapp.data.model.DataOfSpecificHour
import com.example.weatherapp.data.model.HourlyWeatherData
import com.example.weatherapp.util.IconMapper
import com.example.weatherapp.util.Resource
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Card that displays hourly forecast data
 * */
@Composable
fun HourlyValuesCard(hourlyWeatherResource: Resource<HourlyWeatherData>) {
    val hasFinishedLoading = when (hourlyWeatherResource) {
        is Resource.Success -> true
        else -> false
    }
    val dataList: List<DataOfSpecificHour> = when (hourlyWeatherResource) {
        is Resource.Success -> hourlyWeatherResource.data?.listOfDataPerHour!!
        is Resource.Loading -> listOf()
        else -> listOf()
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE4E4E4)),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
        modifier = Modifier.padding(horizontal = 22.dp, vertical = 12.dp),
        shape = RoundedCornerShape(22.dp),
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            if (hasFinishedLoading) {
                items(dataList) { data ->
                    HourlyForecast(data.time, data.weather[0].icon, data.weatherNumbers.temperature)
                }
            }
        }
    }
}

/**
 * Displays the forecast data for one hour in a column including the hour,
 * the weather icon and the temperature.
 * */
@Composable
fun HourlyForecast(time: String, iconID: String, temperature: Double) {
    val iconMapper = IconMapper()
    val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    var localDateTime = LocalDateTime.parse(time, pattern)
    localDateTime = localDateTime.withMinute(0)
    val hour = localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))


    Column(
        modifier = Modifier.width(80.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = hour, fontWeight = FontWeight.Bold)
        Image(
            painter = iconMapper.getIconResource(iconId = iconID),
            contentDescription = "weatherIcon",
            modifier = Modifier.width(30.dp)
        )
        Text(text = temperature.toInt().toString() + "°", fontWeight = FontWeight.Bold)
    }
}



