package com.example.weatherapp.ui.components

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import com.example.weatherapp.model.Place
import com.example.weatherapp.ui.screens.Screen
import com.example.weatherapp.viewmodel.LocationViewmodel
import com.example.weatherapp.viewmodel.PlaceViewmodel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun LocationCard(
    place: Place,
    navController: NavHostController,
    placeViewmodel: PlaceViewmodel,
    locationViewmodel: LocationViewmodel
) {

    val context = LocalContext.current
    var userLocation by remember { mutableStateOf(Place("No Location","-",0.0,0.0)) }
    var locationPermission by remember { mutableStateOf(false) }
    var getLocationPlaceName by remember {
       mutableStateOf(false)
    }
    LaunchedEffect(getLocationPlaceName) {
        if (userLocation != Place("No Location","-",0.0,0.0)) {
            withContext(Dispatchers.IO) {
                locationViewmodel.getPlaceName(locationViewmodel.userLocation.value)
            }
        }

    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ){}

    //check for permission and get permission
    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            locationPermission = true
        }
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE4E4E4),
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),
        modifier = Modifier
            .padding(horizontal = 22.dp)
            .padding(vertical = 12.dp)
            .clickable {
                placeViewmodel.setSelectedPlace(place)
                navController.navigate(Screen.PlaceWeatherScreen.route)
                placeViewmodel.selectedPlace.value?.let { Log.e("set Place", it.placeName) }
            },
        shape = RoundedCornerShape(22.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(22.dp),
            ) {
                Row() {
                    Text(
                        text = place.placeName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.Black,
                    )
                    Column(horizontalAlignment = Alignment.End) {
                        Icon(
                            Icons.Default.Place,
                            contentDescription = "Location",
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .size(26.dp)
                        )
                    }

                }
                Text(
                    text = place.creationDate,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    color = Color.Black,
                )

            }

            Icon(
                Icons.Default.Refresh,
                contentDescription = "Location",
                modifier = Modifier
                    .padding(end = 22.dp)
                    .clickable {
                        getLastKnownLocation(context) { place ->
                            userLocation = place
                            Log.e("res",place.toString())
                            locationViewmodel.userLocation.value = Place("Get Location","Now",place.latitude,place.longitude)
                            getLocationPlaceName = !getLocationPlaceName
                        }
                    })


        }
    }


}

fun getLastKnownLocation(context: Context, onLocationResult: (Place) -> Unit) {
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
        ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val locationString = "Latitude: ${location.latitude}, Longitude: ${location.longitude}"
                Log.e("Location", locationString)
                onLocationResult(Place("-","-",location.latitude,location.longitude))
            } else {
                //Location not found
                onLocationResult(Place("No Location","-",0.0,0.0))
            }
        }.addOnFailureListener {
            //Failed to get Location
            onLocationResult(Place("No Location","-",0.0,0.0))
        }
    } else {
        //no permission
        onLocationResult(Place("No Location","-",0.0,0.0))
    }
}