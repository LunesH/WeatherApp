package com.example.weatherapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.weatherapp.data.api.util.Resource
import com.example.weatherapp.data.model.WeatherData

@Composable
fun ExtraWeatherInfo(weatherResource: Resource<WeatherData>){
    val humidity = when (weatherResource) {
        is Resource.Success -> weatherResource.data?.weatherNumbers?.humidity
        is Resource.Loading -> "Loading..."
        else -> "No info"
    }
    val windSpeed = when (weatherResource) {
        is Resource.Success -> weatherResource.data?.wind?.speed
        is Resource.Loading -> "Loading..."
        else -> "No info"
    }
    val pressure = when (weatherResource) {
        is Resource.Success -> weatherResource.data?.weatherNumbers?.pressure
        is Resource.Loading -> "..."
        else -> "..."
    }
    val feelsLike = when (weatherResource) {
        is Resource.Success -> weatherResource.data?.weatherNumbers?.feelsLike
        is Resource.Loading -> "Loading..."
        else -> "No info"
    }

    Column {
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
        ) {
            Card (
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 12.dp)
                    .weight(1f)
                    .aspectRatio(1f),
                shape = RoundedCornerShape(22.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE4E4E4),
                )
            ){
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ){
                    Box(modifier = Modifier
                        .heightIn(64.dp, 64.dp)
                        .padding(top = 0.dp, bottom = 0.dp)){
                        Image(
                            painter = painterResource(id = R.drawable.water_drop),
                            contentDescription = "Water drop",
                            modifier = Modifier.fillMaxSize()
                        )

                    }
                    Text(
                        text = "Humidity",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .padding(bottom = 0.dp, top = 2.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = humidity.toString()+"%",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
            Card (
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 12.dp)
                    .weight(1f)
                    .aspectRatio(1f),
                shape = RoundedCornerShape(22.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE4E4E4),
                )
            ){
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ){
                    Box(modifier = Modifier
                        .heightIn(64.dp, 64.dp)
                        .padding(top = 0.dp, bottom = 0.dp)){
                        Image(
                                painter = painterResource(id = R.drawable.wind),
                                contentDescription = "Wind",
                                modifier = Modifier.fillMaxSize()
                        )

                    }
                    Text(
                        text = "Wind",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .padding(bottom = 0.dp, top = 2.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = windSpeed.toString()+" km/h",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
        ){
            Card (
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 12.dp)
                    .weight(1f)
                    .aspectRatio(1f),
                shape = RoundedCornerShape(22.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE4E4E4),
                )
            ){
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ){
                    Box(modifier = Modifier
                        .heightIn(64.dp, 64.dp)
                        .padding(top = 0.dp, bottom = 0.dp)){
                        Image(
                            painter = painterResource(id = R.drawable.air_pressure),
                            contentDescription = "Air pressure",
                            modifier = Modifier.fillMaxSize()
                        )

                    }
                    Text(
                        text = "Pressure",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .padding(bottom = 0.dp, top = 2.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = pressure.toString()+" hPa",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
            Card (
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 12.dp)
                    .weight(1f)
                    .aspectRatio(1f),
                shape = RoundedCornerShape(22.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE4E4E4),
                )
            ){
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ){
                    Box(modifier = Modifier
                        .heightIn(64.dp, 64.dp)
                        .padding(top = 0.dp, bottom = 0.dp)){
                        Image(
                            painter = painterResource(id = R.drawable.temperature),
                            contentDescription = "Temperature",
                            modifier = Modifier.fillMaxSize()
                        )

                    }
                    Text(
                        text = "Feels like",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .padding(bottom = 0.dp, top = 2.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = feelsLike.toString()+"°",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

