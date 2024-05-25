package com.example.weatherapp.util

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BackButton(navController: NavController) {
    IconButton(
        onClick = { navController.popBackStack() },
        modifier = Modifier.width(50.dp)
    ) {
        Icon( imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back to homescreen",
            tint = Color.Black)
    }
}
