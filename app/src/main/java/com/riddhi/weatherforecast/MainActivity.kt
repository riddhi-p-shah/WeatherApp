package com.riddhi.weatherforecast

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.riddhi.weatherforecast.screens.HomeScreen
import com.riddhi.weatherforecast.screens.SearchScreen
import com.riddhi.weatherforecast.ui.theme.WeatherForecastTheme
import com.riddhi.weatherforecast.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

//if given more time.. I could make the UI more better and aesthetic.
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherForecastTheme {
                //Using a Scaffold to provide a basic layout structure
                // and WeatherForecastTheme for consistent styling.
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    WeatherApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

/**
 * The main composable function for the Weather Forecast application.
 * This function uses Jetpack Navigation to manage different screens within the application.
 */
@Composable
fun WeatherApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    // Defines the navigation host with the initial screen and animations.
    NavHost(navController = navController, startDestination = "home",
        enterTransition = {
            fadeIn(
                animationSpec = tween(durationMillis = 300),
                initialAlpha = 0.99f
            )
        }, exitTransition = {
            fadeOut(
                animationSpec = tween(durationMillis = 300),
                targetAlpha = 0.99f
            )
        }) {
        val noEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
            {
                fadeIn(
                    animationSpec = tween(durationMillis = 300),
                    initialAlpha = 0.99f
                )
            }

        val noExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
            {
                fadeOut(
                    animationSpec = tween(durationMillis = 300),
                    targetAlpha = 0.99f
                )
            }

        composable(
            route = Constants.HOME_SCREEN,
            enterTransition = noEnterTransition,
            exitTransition = noExitTransition
        ) { entry ->
            val screenReturnedState =
                entry.savedStateHandle.getStateFlow(Constants.SELECTED_LOCATION, false)

            //the "home" screen composable with its route, animations,
            HomeScreen(
                modifier = modifier,
                isScreenReturned = screenReturnedState.value ?: false,
                onSearchClick = {
                    navController.navigate(Constants.SEARCH_SCREEN)
                })
        }
        composable(
            route = Constants.SEARCH_SCREEN,
            enterTransition = noEnterTransition,
            exitTransition = noExitTransition
        )
        {
            ///The search screen composable with its route, animations,
            // onBack callback for navigation, and onSelect callback for handling
            // user selection of a location.
            SearchScreen(modifier = modifier, onBack = {
                navController.popBackStack()
            }) { geoCodeModel ->
                Log.d("geoCodeModel", geoCodeModel.toString())
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    Constants.SELECTED_LOCATION,
                    true
                )
                navController.popBackStack()
            }
        }
    }
}
