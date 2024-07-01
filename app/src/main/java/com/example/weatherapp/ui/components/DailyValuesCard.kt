package com.example.weatherapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.weatherapp.data.api.util.Resource
import com.example.weatherapp.model.DailyWeatherData
import com.example.weatherapp.model.DataOfSpecificDay
import com.example.weatherapp.model.Temperature
import com.example.weatherapp.util.IconMapper
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DailyValuesCard(dailyWeatherResource: Resource<DailyWeatherData>) {
    val hasFinishedLoading= when(dailyWeatherResource){
        is Resource.Success -> true
        else -> false
    }
    val dataList:List<DataOfSpecificDay> = when (dailyWeatherResource) {
        is Resource.Success -> dailyWeatherResource.data?.list!!
        is Resource.Loading -> listOf()
        else -> listOf()
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE4E4E4)),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
        modifier = Modifier.padding(horizontal = 22.dp, vertical = 12.dp),
        shape = RoundedCornerShape(22.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal=22.dp)
        ) {
            if(hasFinishedLoading) {
                dataList.forEach { data ->
                    DailyForecast(data.timeOfForecast, data.weather[0].icon, data.temperature)
                    Divider(modifier = Modifier
                        .padding(vertical = 4.dp),
                        thickness = 2.dp,)
                }
            }
        }
    }
}

@Composable
fun DailyForecast(time: Int, iconID: String, temperature: Temperature) {
    val iconMapper = IconMapper();
    val instant = java.time.Instant.ofEpochSecond(time.toLong())
    val date = LocalDateTime.ofInstant(instant, java.time.ZoneId.systemDefault())
    val currentDate = LocalDate.now(java.time.ZoneId.systemDefault())
    val dayOfWeek = if (date.toLocalDate().isEqual(currentDate)) {
        "Today"
    } else {
        date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.UK)
    }
    Row (modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
        ) {
        Text(text= dayOfWeek, fontWeight=FontWeight.Bold, modifier = Modifier.width(90.dp))
        Image(
            painter = iconMapper.getIconResource(iconId = iconID),
            contentDescription = "weatherIcon",
            modifier = Modifier.width(30.dp))
        Text(text = temperature.tempMin.toInt().toString()+"°" +"- "+ temperature.tempMax.toInt().toString()+"°", modifier = Modifier.width(80.dp), fontWeight = FontWeight.Bold)
    }
}
