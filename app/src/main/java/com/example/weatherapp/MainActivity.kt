package com.example.weatherapp

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.database.PlaceDatabase
import com.example.weatherapp.model.Place
import com.example.weatherapp.ui.screens.AddPlaceScreen
import com.example.weatherapp.ui.screens.PlaceWeatherScreen
import com.example.weatherapp.ui.screens.Screen
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.viewmodel.LocationViewmodel
import com.example.weatherapp.viewmodel.PlaceViewmodel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    companion object {
        lateinit var database: PlaceDatabase
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Room.databaseBuilder(
            applicationContext,
            PlaceDatabase::class.java,
            "place_database"
        ).fallbackToDestructiveMigration()
            .build()



        setContent {
            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherApp()
                }
            }
        }
    }
}

@Composable
fun WeatherApp() {
    val navController = rememberNavController();
    val placeViewmodel = PlaceViewmodel()
    val locationViewmodel = LocationViewmodel()



    LaunchedEffect(key1 = true){
        CoroutineScope(Dispatchers.IO).launch {
            var storedPlaces = MainActivity.database.placeDao().getAllPlaces()
            placeViewmodel.placesList+=storedPlaces
        }
    }

    LaunchedEffect(true) {
        withContext(Dispatchers.IO) {
            placeViewmodel.placesList.forEach{place ->
                placeViewmodel.updatePlaceCoordinates(place)
            }
        }
    }
    Scaffold { innerPadding ->
        WeatherNavHost(navController, Modifier.padding(innerPadding), placeViewmodel,locationViewmodel)
    }
}

@Composable
fun WeatherNavHost(
    navController: NavHostController,
    padding: Modifier,
    placeViewmodel: PlaceViewmodel,
    locationViewmodel: LocationViewmodel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.AddPlaceScreen.route,
        ){
        composable(route = Screen.AddPlaceScreen.route){
            AddPlaceScreen(navController,placeViewmodel,locationViewmodel)
        }
        composable(route = Screen.PlaceWeatherScreen.route){
            PlaceWeatherScreen(navController, placeViewmodel)
        }
    }
}

