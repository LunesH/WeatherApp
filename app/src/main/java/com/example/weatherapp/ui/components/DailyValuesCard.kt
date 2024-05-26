package com.example.weatherapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R

@Composable
fun DailyValuesCard() {
    val testList: List<Test> = listOf(
        Test("test1"),
        Test("test2"),
        Test("test3"),
        Test("test4"),
        Test("test5"),
        Test("test6"),
        Test("test7")
    )
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE4E4E4)),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
        modifier = Modifier.padding(horizontal = 22.dp, vertical = 12.dp),
        shape = RoundedCornerShape(22.dp),
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items(testList) { test ->
                HourlyForecast(test.test)
            }
        }
    }
}

@Composable
fun HourlyForecast(test: String) {
    Column (modifier = Modifier.width(80.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = test)
        Image(
            painter = painterResource(id = R.drawable.sun),
            contentDescription = "weatherIcon",
            modifier = Modifier.width(30.dp))
        Text(text = test)
    }
}

data class Test(val test: String)

