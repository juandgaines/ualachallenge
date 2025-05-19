package com.juandgaines.challengeplaces.presentation

import android.content.Context
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.primitives.Ints
import com.juandgaines.challengeplaces.R
import com.juandgaines.challengeplaces.domain.city.City
import com.juandgaines.challengeplaces.ui.theme.ChallengePlacesTheme
import com.juandgaines.challengeplaces.utils.providerCities
import org.junit.Rule
import org.junit.Test

class DetailPlacesTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testDetailScreenUi_noMarkerState() {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        composeTestRule.setContent {
            ChallengePlacesTheme {
                DetailPlaces (
                    state = SearchState(
                        isLoading = false,
                        suggestions = emptyList(),
                        currentSelectedCity = null,

                    ),
                    isDetailVisible = false,
                    onBackPressed = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription(
            context.getString(R.string.map_screen_description)
        ).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(
            context.getString(R.string.map_screen_marker_description)
        ).assertIsNotDisplayed()

    }

    @Test
    fun testDetailScreenUi_withMarkerState() {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        composeTestRule.setContent {
            ChallengePlacesTheme {
                DetailPlaces (
                    state = SearchState(
                        isLoading = false,
                        suggestions = emptyList(),
                        currentSelectedCity = providerCities[0],

                        ),
                    isDetailVisible = false,
                    onBackPressed = {}
                )
            }
        }
        composeTestRule.waitForIdle()


        composeTestRule.waitUntil(5000){
            composeTestRule.onAllNodesWithContentDescription(
                context.getString(R.string.map_screen_marker_description)
            ).fetchSemanticsNodes().size == 1
        }

    }


}