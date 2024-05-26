package com.example.weatherapp.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import com.example.weatherapp.model.Place
import com.example.weatherapp.ui.components.PlaceCard
import com.example.weatherapp.ui.components.TopAppTitle
import com.example.weatherapp.viewmodel.PlaceViewmodel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlaceScreen(navController: NavHostController, placeViewmodel: PlaceViewmodel) {

    var searchQuery by remember { mutableStateOf("") }
    var searchResult by remember { mutableStateOf("No search yet") }
    Scaffold(
        topBar = {
            Surface (shadowElevation = 16.dp) {
                TopAppBar(
                    title = {
                        TopAppTitle()
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color(
                            0xffF5F5F5
                        )
                    )
                )
            }
        },
        content = { innerPadding ->
            Modifier.padding(innerPadding)
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(Color(0xffD2D2D2))
            ) {
                Spacer(modifier = Modifier.height(88.dp))
                SearchBar(placeViewmodel) { query ->
                    searchQuery = query
                    searchResult = "Search query: $query"
                }
                Spacer(modifier = Modifier.height(10.dp))
                PlaceCard(Place("Bremen", "location", 0.0, 0.0), navController, placeViewmodel)
                Divider(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(vertical = 4.dp),
                    thickness = 2.dp,
                    color = Color(0xFFE4E4E4),
                );
                placeViewmodel.placesList.forEach { place ->
                    PlaceCard(
                        place = place,
                        navController = navController,
                        placeViewmodel = placeViewmodel
                    )
                }

            }
        })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(placeViewmodel: PlaceViewmodel, onSearch: (String) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    var addedPlace by remember {
        mutableStateOf(Place("", "", 0.0, 0.0))
    }
    LaunchedEffect(addedPlace) {
        if (addedPlace != Place("", "", 0.0, 0.0)) {
            withContext(Dispatchers.IO) {
                placeViewmodel.updatePlaceCoordinates(addedPlace)
            }
        }

    }
    LaunchedEffect(searchQuery) {
        withContext(Dispatchers.IO) {
            placeViewmodel.updateAutomcompletePlaceList(searchQuery)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Color(0xFFE4E4E4), shape = RoundedCornerShape(32.dp)),


        ) {
        Column {
            TextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    onSearch(it)
                },
                placeholder = {
                    Text("Add a place")
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black,
                ),
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearch.invoke(searchQuery)
                    }
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .padding(start = 8.dp)
                    )
                },
                textStyle = LocalTextStyle.current.copy(color = Color.Black)
            )

            if (placeViewmodel.placeAutocompletionList.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp)
                ) {
                    placeViewmodel.placeAutocompletionList.forEach { placename ->
                        Divider(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .padding(bottom = 16.dp),
                            thickness = 2.dp,
                            color = Color.LightGray,
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                                    val date = LocalDateTime
                                        .now()
                                        .format(formatter)
                                    var place = Place(placename, date, 0.0, 0.0)
                                    placeViewmodel.placesList += place
                                    searchQuery = "";
                                    //trigger coordination update
                                    addedPlace = place
                                }
                        ) {
                            Text(text = placename, modifier = Modifier.padding(bottom = 8.dp))
                        };
                    }


                };

            }
        }

    }
}

fun getLocation(context:Context){
    lateinit var fusedLocationClient: FusedLocationProviderClient
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        Log.e("res",ActivityCompat.checkSelfPermission(context,
            Manifest.permission.ACCESS_FINE_LOCATION).toString())
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return
    }
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location->
            if (location != null) {
                // use your location object
                // get latitude , longitude and other info from this
                Log.e("loc","sdada")
            }

        }

}