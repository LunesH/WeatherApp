package com.example.weatherapp.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.weatherapp.MainActivity
import com.example.weatherapp.data.model.Place
import com.example.weatherapp.ui.viewmodel.PlaceViewmodel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * A search bar containg the logic to request place autocompletion and the adding of a chosen place.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(placeViewmodel: PlaceViewmodel, onSearch: (String) -> Unit) {

    var context = LocalContext.current


    var searchQuery by remember { mutableStateOf("") }
    var addedPlace by remember {
        mutableStateOf(Place(placeName = "", creationDate = "-", latitude = 0.0, longitude = 0.0))
    }

    //trigger LaunchedEffect if addedPlace is changed
    LaunchedEffect(addedPlace) {
        //check if addedPlace is innit state
        if (addedPlace != Place(placeName = "", creationDate = "-", latitude = 0.0, longitude = 0.0)) {
            //get the place coordinates and insert in RoomDb
            withContext(Dispatchers.IO) {
                var newPlace = addedPlace
                placeViewmodel.updatePlaceCoordinates(addedPlace)

                val index = placeViewmodel.placesList.indexOfFirst { newPlace.placeName == it.placeName }
                newPlace = placeViewmodel.placesList[index]
                MainActivity.database.placeDao().insertPlace(Place(placeName = newPlace.placeName, creationDate = newPlace.creationDate, latitude = newPlace.latitude, longitude = newPlace.longitude))

            }
        }

    }
    //Trigger autocompletion if searchQuery is changed
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
                                    var place = Place(
                                        placeName = placename,
                                        creationDate = date,
                                        latitude = 0.0,
                                        longitude = 0.0
                                    )
                                    placeViewmodel.placesList.forEach { it ->
                                        if (it.placeName.equals(place.placeName)) {
                                            val toast = Toast.makeText(
                                                context,
                                                "Place " + place.placeName + " already added!",
                                                Toast.LENGTH_SHORT
                                            )
                                            toast.show()

                                            return@clickable
                                        }
                                    }
                                    placeViewmodel.placesList += place
                                    searchQuery = "";
                                    //trigger coordinates update
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

