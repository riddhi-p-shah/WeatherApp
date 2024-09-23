package com.riddhi.weatherforecast.screens


import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.riddhi.weatherforecast.models.Main
import com.riddhi.weatherforecast.models.Weather
import com.riddhi.weatherforecast.models.WeatherResponseModel
import com.riddhi.weatherforecast.viewmodels.HomeViewModel
import kotlin.math.roundToInt
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.riddhi.weatherforecast.R
import com.riddhi.weatherforecast.api.ApiState
import com.riddhi.weatherforecast.utils.Utils

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit,
    isScreenReturned: Boolean = false
) {
    val homeScreenViewModel: HomeViewModel = hiltViewModel()
    val weatherApiState = homeScreenViewModel.weatherApiState.collectAsState()

    val onPermissionGranted = {
        homeScreenViewModel.permissionGranted = true
        homeScreenViewModel.getLastUserLocation()
    }

    val onPermissionDenied: () -> Unit = {
        homeScreenViewModel.permissionGranted = false
        homeScreenViewModel.message.value = "Please allow location permission"
    }

    val onPermissionsRevoked: () -> Unit = {
        homeScreenViewModel.permissionGranted = false
        homeScreenViewModel.message.value = "Please allow location permission"
    }

    RequestLocationPermission(
        onPermissionGranted = onPermissionGranted,
        onPermissionDenied = onPermissionDenied,
        onPermissionsRevoked = onPermissionsRevoked
    )

    LaunchedEffect(key1 = isScreenReturned) {
        homeScreenViewModel.getWeather()
    }

    Column(modifier = modifier.fillMaxWidth()) {
        SearchComponent(onSearchClick = onSearchClick)
        when (weatherApiState.value) {
            is ApiState.Error -> {
                val message = (weatherApiState.value as ApiState.Error).message
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
                val weatherData =
                    (weatherApiState.value as ApiState.Success<WeatherResponseModel>).data
                WeatherDataComponent(weatherData = weatherData)
            }
        }
    }
}

@Composable
fun WeatherDataComponent(modifier: Modifier = Modifier, weatherData: WeatherResponseModel?) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        WeatherCard(weatherData = weatherData)
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            DataCard(
                modifier = Modifier.weight(1f),
                title = stringResource(id = R.string.wind),
                imagePainter = painterResource(id = R.drawable.air),
                value = "${weatherData?.wind?.speed}m/h"
            )
            Spacer(modifier = Modifier.width(16.dp))
            DataCard(
                modifier = Modifier.weight(1f),
                title = stringResource(id = R.string.humidity),
                imagePainter = painterResource(id = R.drawable.humidity),
                value = "${weatherData?.main?.humidity}%"
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            DataCard(
                modifier = Modifier.weight(1f),
                title = stringResource(id = R.string.sunrise),
                imagePainter = painterResource(id = R.drawable.sunrise),
                value = Utils.get12hFormattedTime(
                    weatherData?.sys?.sunrise,
                    timezoneOffsetHours = weatherData?.timezone ?: 0
                )
                    ?: "06:00 AM"
            )
            Spacer(modifier = Modifier.width(16.dp))
            DataCard(
                modifier = Modifier.weight(1f),
                title = stringResource(id = R.string.sunset),
                imagePainter = painterResource(id = R.drawable.sunset),
                value = Utils.get12hFormattedTime(
                    weatherData?.sys?.sunset,
                    timezoneOffsetHours = weatherData?.timezone ?: 0
                )
                    ?: "06:00 PM"
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            DataCard(
                modifier = Modifier.weight(1f),
                title = stringResource(id = R.string.rain),
                imagePainter = painterResource(id = R.drawable.rain),
                value = "${weatherData?.rain?.rain ?: 0} mm/h"
            )
            Spacer(modifier = Modifier.width(16.dp))
            DataCard(
                modifier = Modifier.weight(1f),
                title = stringResource(id = R.string.snow),
                imagePainter = painterResource(id = R.drawable.snow),
                value = "${weatherData?.snow?.snow ?: 0} mm/h"
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchComponent(onSearchClick: () -> Unit) {
    TextField(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .clickable {
            onSearchClick()
        },
        enabled = false,
        readOnly = true,
        value = stringResource(id = R.string.search_for_city),
        leadingIcon = {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Location Icon")
        },
        onValueChange = {
            //do nothing
        }
    )
}

@Composable
fun DataCard(modifier: Modifier = Modifier, title: String, value: String, imagePainter: Painter) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            TitleComponent(title = title)
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = value, style = MaterialTheme.typography.bodyMedium)
                Image(
                    modifier = Modifier.size(size = 40.dp),
                    painter = imagePainter,
                    contentDescription = "$title icon"
                )
            }
        }

    }
}

@Composable
fun WeatherCard(modifier: Modifier = Modifier, weatherData: WeatherResponseModel?) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TitleComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                title = "${weatherData?.name}"
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                TemperatureComponent(modifier = Modifier.weight(1f), main = weatherData?.main)
                WeatherComponent(weather = weatherData?.weather?.get(0))
            }
        }
    }

}

@Composable
fun TitleComponent(modifier: Modifier = Modifier, title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        modifier = modifier.padding(4.dp)
    )
}

@Composable
fun TemperatureComponent(modifier: Modifier = Modifier, main: Main?) {
    Column(
        modifier = modifier.padding(4.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "${main?.temp?.roundToInt()}°F",
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Feels Like ${main?.feelsLike?.roundToInt()}°F",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Min: ${main?.tempMin?.roundToInt()}°F",
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(
            text = "Max: ${main?.tempMax?.roundToInt()}°F",
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
fun WeatherComponent(modifier: Modifier = Modifier, weather: Weather?) {
    Column(
        modifier = modifier.padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${weather?.description}",
            style = MaterialTheme.typography.titleSmall
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(weather?.iconUrl)
                .error(android.R.drawable.stat_notify_error)
                .crossfade(true)
                .build(),
            contentDescription = "Weather Icon",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(80.dp)
                .height(80.dp)
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    onPermissionsRevoked: () -> Unit,
) {

    val permissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )
    LaunchedEffect(key1 = permissionState.allPermissionsGranted) {
        val allPermissionsRevoked =
            permissionState.permissions.size == permissionState.revokedPermissions.size

        // Filter permissions that need to be requested.
        val permissionsToRequest = permissionState.permissions.filter {
            !it.status.isGranted
        }

        // If there are permissions to request, launch the permission request.
        if (permissionsToRequest.isNotEmpty()) permissionState.launchMultiplePermissionRequest()

        // Execute callbacks based on permission status.
        if (allPermissionsRevoked) {
            onPermissionsRevoked()
        } else {
            if (permissionState.allPermissionsGranted) {
                onPermissionGranted()
            } else {
                onPermissionDenied()
            }
        }
    }


}