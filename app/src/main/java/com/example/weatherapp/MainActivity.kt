package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.ui.screens.AddPlaceScreen
import com.example.weatherapp.ui.screens.PlaceWeatherScreen
import com.example.weatherapp.ui.screens.Screen
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.viewmodel.PlaceViewmodel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherApp() {
    val navController = rememberNavController();
    val placeViewmodel = PlaceViewmodel();
    LaunchedEffect(true) {
        withContext(Dispatchers.IO) {
            placeViewmodel.placesList.forEach{place ->
                placeViewmodel.getPlaceCoordinates(place)
            }
        }
    }
    Scaffold(
        topBar = {
            Surface (shadowElevation = 16.dp) {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            color = Color.Black
                        )
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color(
                            0xffF5F5F5
                        )
                    ),
                )
            }
        },
        content = { innerPadding -> WeatherNavHost(navController, Modifier.padding(innerPadding),placeViewmodel) },
    )
}

@Composable
fun WeatherNavHost(
    navController: NavHostController,
    padding: Modifier,
    placeViewmodel: PlaceViewmodel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.AddPlaceScreen.route,
        ){
        composable(route = Screen.AddPlaceScreen.route){
            AddPlaceScreen(navController,placeViewmodel)
        }
        composable(route = Screen.PlaceWeatherScreen.route){
            PlaceWeatherScreen(navController)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherAppTheme {
        Greeting("Android")
    }
}