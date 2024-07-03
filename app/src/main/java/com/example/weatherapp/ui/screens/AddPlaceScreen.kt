package com.example.weatherapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.weatherapp.MainActivity
import com.example.weatherapp.data.model.Place
import com.example.weatherapp.ui.components.LocationCard
import com.example.weatherapp.ui.components.PlaceCard
import com.example.weatherapp.ui.components.TopAppTitle
import com.example.weatherapp.ui.viewmodel.LocationViewmodel
import com.example.weatherapp.ui.viewmodel.PlaceViewmodel
import com.example.weatherapp.ui.components.SearchBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * This is the screen where the user adds places and can access the weather of the added places.
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlaceScreen(
    navController: NavHostController,
    placeViewmodel: PlaceViewmodel,
    locationViewmodel: LocationViewmodel
) {
    var searchQuery by remember { mutableStateOf("") }
    var searchResult by remember { mutableStateOf("No search yet") }


    Scaffold(
        topBar = {
            Surface(shadowElevation = 16.dp) {
                TopAppBar(
                    title = { TopAppTitle() },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xffF5F5F5))
                )
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(Color(0xffD2D2D2))
                    .padding(innerPadding)
            ) {
                Spacer(modifier = Modifier.height(64.dp))
                SearchBar(placeViewmodel) { query ->
                    searchQuery = query
                    searchResult = "Search query: $query"
                }
                Spacer(modifier = Modifier.height(10.dp))
                LocationCard(locationViewmodel.userLocation.value, navController, placeViewmodel,locationViewmodel)
                Divider(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(vertical = 4.dp),
                    thickness = 2.dp,
                    color = Color(0xFFE4E4E4)
                )
                if (placeViewmodel.placesList.isEmpty()){
                    //Placeholder if no place is added
                    Text(
                        text = "Add a place to see the local weather" ,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .padding(top = 32.dp)
                    )
                }else{
                    //list of added places
                    LazyColumn (modifier = Modifier.fillMaxHeight()){
                        placeViewmodel.placesList.forEach { place ->
                            item {
                                PlaceCard(
                                place = place,
                                navController = navController,
                                placeViewmodel = placeViewmodel
                                )
                            }

                        }
                    }

                }

            }
        }
    )
}

