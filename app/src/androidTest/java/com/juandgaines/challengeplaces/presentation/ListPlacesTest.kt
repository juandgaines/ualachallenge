package com.juandgaines.challengeplaces.presentation

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.juandgaines.challengeplaces.R
import com.juandgaines.challengeplaces.ui.theme.ChallengePlacesTheme
import com.juandgaines.challengeplaces.utils.providerCities
import org.junit.Rule
import org.junit.Test

class ListPlacesTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testListScreenUi_startState() {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        val query = ""
        composeTestRule.setContent {
            ChallengePlacesTheme {
                ListPlaces (
                    state = SearchState(
                        isLoading = false,
                        suggestions = emptyList(),
                        currentSelectedCity = null,

                    ),
                    query = query,
                    onAction = {},
                    navigateToDetail = {}
                )
            }
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithContentDescription(
            context.getString(R.string.search_screen_description)
        ).assertIsDisplayed()

        composeTestRule.onNodeWithText(
            context.getString(R.string.location_not_found, query)
        ).assertIsNotDisplayed()

        composeTestRule.onNodeWithText(
            context.getString(R.string.start)
        ).assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription(
            context.getString(R.string.loading_description)
        ).assertIsNotDisplayed()


    }

    @Test
    fun testListScreenUi_notFoundState() {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        val query = "qweklqwjeqlkjwelkqlwe"
        composeTestRule.setContent {
            ChallengePlacesTheme {
                ListPlaces (
                    state = SearchState(
                        isLoading = false,
                        suggestions = providerCities.filter { it.name.startsWith(query) },
                        currentSelectedCity = null,
                        ),
                    query = query,
                    onAction = {},
                    navigateToDetail = {}
                )
            }
        }
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithContentDescription(
            context.getString(R.string.search_screen_description)
        ).assertIsDisplayed()

        composeTestRule.onNodeWithText(
            context.getString(R.string.location_not_found, query)
        ).assertIsDisplayed()


        composeTestRule.onNodeWithContentDescription(
            context.getString(R.string.loading_description)
        ).assertIsNotDisplayed()
    }


    @Test
    fun testListScreenUi_listDepicted() {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        val query = "Al"
        composeTestRule.setContent {
            ChallengePlacesTheme {
                ListPlaces (
                    state = SearchState(
                        isLoading = false,
                        suggestions = providerCities.filter { it.name.startsWith(query) },
                        currentSelectedCity = null,
                    ),
                    query = query,
                    onAction = {},
                    navigateToDetail = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription(
            context.getString(R.string.search_screen_description)
        ).assertIsDisplayed()

        composeTestRule.onNodeWithText(
            context.getString(R.string.location_not_found, query)
        ).assertIsNotDisplayed()


        composeTestRule.onNodeWithContentDescription(
            context.getString(R.string.loading_description)
        ).assertIsNotDisplayed()

        composeTestRule.onNodeWithContentDescription(
            context.getString(R.string.search_screen_list_description)
        ).assertIsDisplayed()
    }

}