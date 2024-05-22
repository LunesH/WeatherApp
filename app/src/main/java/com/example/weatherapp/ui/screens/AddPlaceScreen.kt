package com.example.weatherapp.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.weatherapp.model.Place
import com.example.weatherapp.util.PlaceCard
import com.example.weatherapp.viewmodel.PlaceViewmodel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun AddPlaceScreen(navController: NavHostController, placeViewmodel: PlaceViewmodel) {

    var searchQuery by remember { mutableStateOf("") }
    var searchResult by remember { mutableStateOf("No search yet") }






    Column (
        modifier = Modifier
            .fillMaxHeight()
            .background(Color(0xffD2D2D2))
    ){
        Spacer(modifier = Modifier.height(64.dp))
        Text(text = "Add Place Screen")
        Button(onClick = {
             //navController.navigate(Screen.PlaceWeatherScreen.route)
        },
            modifier = Modifier
                .width(150.dp)
        )
        {

        }
        SearchBar (placeViewmodel){ query ->
            searchQuery = query
            searchResult = "Search query: $query"
        }
        PlaceCard(Place("Bremen","01-05-2024",0.0,0.0),navController)
        Divider(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(vertical = 4.dp),
            thickness = 2.dp,
            color = Color(0xFFE4E4E4),
        );
        placeViewmodel.placesList.forEach {place ->
            PlaceCard(place = place, navController = navController)
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(placeViewmodel: PlaceViewmodel, onSearch: (String) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    
    LaunchedEffect(searchQuery) {
        withContext(Dispatchers.IO) {
            placeViewmodel.updateAutomcompletePlaceList(searchQuery)
            var res = placeViewmodel.placeAutocompletionList
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
                Column (
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp)
                ){
                   placeViewmodel.placeAutocompletionList.forEach{ placename ->
                        Divider(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .padding(bottom = 16.dp),
                            thickness = 2.dp,
                            color = Color.LightGray,
                        )
                        Text(text = placename, modifier = Modifier.padding(bottom = 8.dp));
                    }


                };

            }
        }

    }
}