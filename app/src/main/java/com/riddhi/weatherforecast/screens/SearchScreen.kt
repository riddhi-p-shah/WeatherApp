package com.riddhi.weatherforecast.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.riddhi.weatherforecast.R
import com.riddhi.weatherforecast.api.ApiState
import com.riddhi.weatherforecast.models.GeoCodeModel
import com.riddhi.weatherforecast.viewmodels.SearchViewModel

/**
 * This composable displays the search screen and allows selecting a city location.
 *
 * @param modifier The modifier for the composable.
 * @param onBack The callback function to be called when the back button is clicked.
 * @param onSelectLocation The callback function to be called when a location is selected.
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onSelectLocation: (GeoCodeModel) -> Unit,
) {

    // Create a SearchViewModel instance
    val searchViewModel: SearchViewModel = hiltViewModel()

    // Observe the geo API state
    val geoApiState = searchViewModel.geoApiState.collectAsState()

    // Store the search query in a mutable state variable
    var searchQuery by remember {
        mutableStateOf("")
    }

    // Create a FocusRequester to request the focus on the TextField
    val focusRequester = remember { FocusRequester() }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)

    ) {
        TextField(modifier = Modifier
            .focusRequester(focusRequester)
            .fillMaxWidth()
            .background(Color.White),
            value = searchQuery,
            onValueChange = {
                // Update the search query and fetch suggestions
                searchQuery = it
                searchViewModel.getSuggestions(it)
            },
            leadingIcon = {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back Icon")
                }
            },
            placeholder = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Search for a city in format of City, State, Country",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            })

        // Display content based on the API state
        when (geoApiState.value) {
            is ApiState.Error -> {
                val message = (geoApiState.value as ApiState.Error).message
                ApiStatusComponent(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    message = message
                )
            }

            ApiState.Loading -> {
                ApiStatusComponent(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    message = stringResource(
                        id = R.string.loading_message
                    )
                )
            }

            is ApiState.Success<*> -> {
                val suggestions =
                    (geoApiState.value as ApiState.Success<List<GeoCodeModel>>).data

                if (suggestions.isEmpty()) {
                    ApiStatusComponent(message = "No City Found!")
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp), verticalArrangement = Arrangement.Top
                    ) {
                        items(count = suggestions.size) { itemIndex ->
                            val suggestion = suggestions[itemIndex]
                            SuggestionItem(suggestion = suggestion, onSelect = {
                                searchViewModel.saveLocation(suggestion)
                                onSelectLocation(suggestion)
                            })
                        }
                    }
                }
            }
        }
    }
}

/**
 * Displays a suggestion item in the search results list.
 *
 * @param suggestion The geocode suggestion to display.
 * @param onSelect The callback function to be called when the item is selected.
 */
@Composable
fun SuggestionItem(suggestion: GeoCodeModel, onSelect: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val cityName = suggestion.name ?: ""
        val stateName = suggestion.state ?: ""
        val countryName = suggestion.country ?: ""

        Icon(
            modifier = Modifier.weight(0.1f),
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Location Icon"
        )
        Text(
            modifier = Modifier.weight(0.65f),
            text = "$cityName, $stateName, $countryName"
        )
        TextButton(modifier = Modifier.weight(0.25f), onClick = onSelect) {
            Text(text = "Select")
        }
    }
}
