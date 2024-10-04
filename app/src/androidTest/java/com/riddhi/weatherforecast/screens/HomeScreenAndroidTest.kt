package com.riddhi.weatherforecast.screens

import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.riddhi.weatherforecast.R
import org.junit.Rule
import org.junit.Test

//It's failing due to version cross dependency.. need more time to fix this..
class HomeScreenAndroidTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun searchComponent_displaysCorrectText() {
        // Arrange
        var searchText = ""

        // Act
        composeTestRule.setContent {
            searchText = stringResource(id = R.string.search_for_city)
            SearchComponent(onSearchClick = {})
        }

//        composeTestRule.onNodeWithText(searchText)
        val textField = composeTestRule.onNodeWithText(searchText)

        // Assert
        textField.assertIsDisplayed()
        textField.assertTextEquals(searchText)
    }

    @Test
    fun dataCard_displaysTitleValueAndImage() {
        // Arrange
        val title = "Wind"
        val value = "10 m/h"
        val imageResId = R.drawable.air

        // Act
        composeTestRule.setContent {
            DataCard(
                modifier = Modifier,
                title = title,
                value = value,
                imagePainter = painterResource(id = imageResId)
            )
        }
        val titleText = composeTestRule.onNodeWithText(title)
        val valueText = composeTestRule.onNodeWithText(value)

        // Assert
        titleText.assertIsDisplayed()
        titleText.assertTextEquals(title)
        valueText.assertIsDisplayed()
        valueText.assertTextEquals(value)
    }
}