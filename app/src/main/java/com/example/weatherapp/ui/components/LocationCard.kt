package com.example.weatherapp.ui.components

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import android.widget.Toast
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
import com.example.weatherapp.MainActivity
import com.example.weatherapp.data.model.LocationEntity
import com.example.weatherapp.data.model.Place
import com.example.weatherapp.ui.screens.Screen
import com.example.weatherapp.ui.viewmodel.LocationViewmodel
import com.example.weatherapp.ui.viewmodel.PlaceViewmodel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Card containing the location and the time the location
 * was last updated.
 */
@Composable
fun LocationCard(
    place: Place,
    navController: NavHostController,
    placeViewmodel: PlaceViewmodel,
    locationViewmodel: LocationViewmodel
) {

    val context = LocalContext.current
    var userLocation by remember { mutableStateOf(Place(placeName = "No Location", creationDate = "-", latitude = 0.0, longitude = 0.0)) }
    var locationPermission by remember { mutableStateOf(false) }

    var getLocationPlaceName by remember {
       mutableStateOf(false)
    }

    //The LaunchedEffect is triggered if getLocationPlaceName is changed to get new place Name and save it to Roomdb
    LaunchedEffect(getLocationPlaceName) {
        //check if userLocation is Init state
        if (userLocation != Place(placeName = "No Location", creationDate = "-", latitude = 0.0, longitude = 0.0)) {
            withContext(Dispatchers.IO) {
                locationViewmodel.getPlaceName(locationViewmodel.userLocation.value)
                var location = locationViewmodel.userLocation.value
                //update Roomdb in coroutine
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        if (MainActivity.database.placeDao().getAllLocations().isEmpty()){
                            //insert in RoomDb
                            MainActivity.database.placeDao().insertLocation(LocationEntity(0,location.placeName,location.creationDate,location.latitude,location.longitude))
                        }else{
                            //update in RoomDb
                            MainActivity.database.placeDao().updateLocation(LocationEntity(0,location.placeName,location.creationDate,location.latitude,location.longitude))
                        }
                    } catch (e: Exception) {
                        Log.e("Exception", "Error deleting place: ${e.message}", e)
                    }
                }
            }
        }

    }

    //ask for location permission
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ){}

    //check for permission and get permission after first launch
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

    //Location card UI
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
                        getNewLocationData(context) { place ->
                            userLocation = place
                            Log.e("res",place.toString())
                            locationViewmodel.userLocation.value = Place(placeName = "Get Location", creationDate = "Now", latitude = place.latitude, longitude = place.longitude)
                            getLocationPlaceName = !getLocationPlaceName
                        }
                    })


        }
    }


}

/**
 * Callback funtion to get the users location data.
 * */
private fun getNewLocationData(context: Context, onLocationResult: (Place) -> Unit) {
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    val locationRequest = LocationRequest.create().apply {
        interval = 10000 // 10 seconds
        fastestInterval = 5000 // 5 seconds
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
    //check for permission
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
        ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        fusedLocationClient.requestLocationUpdates(locationRequest, object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                fusedLocationClient.removeLocationUpdates(this)
                if (locationResult != null && locationResult.locations.isNotEmpty()) {
                    //getLocation successful
                    val location = locationResult.locations[0]
                    onLocationResult(Place(placeName = "-", creationDate = "-", latitude = location.latitude, longitude = location.longitude))
                } else {
                    //error while getting location
                    onLocationResult(Place(placeName = "No Location", creationDate = "-", latitude = 0.0, longitude = 0.0))
                }
            }
        }, Looper.getMainLooper())
    } else {
        //no location permission
        val toast = Toast.makeText(
            context,
            "Activate location permission in the settings!",
            Toast.LENGTH_SHORT
        )
        toast.show()
        onLocationResult(Place(placeName = "No Permission", creationDate = "-", latitude = 0.0, longitude = 0.0))
    }
}