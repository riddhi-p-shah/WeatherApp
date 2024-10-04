package com.riddhi.weatherforecast.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Reusable component that displays a message related to API status.
 */
@Composable
fun ApiStatusComponent(modifier: Modifier = Modifier, message: String) {
    Text(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        text = message,
        style = MaterialTheme.typography.bodySmall,
        color = Color.Gray,
        textAlign = TextAlign.Center
    )
}