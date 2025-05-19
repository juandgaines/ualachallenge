package com.juandgaines.challengeplaces.presentation

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import com.juandgaines.challengeplaces.MainActivity
import com.juandgaines.challengeplaces.R
import com.juandgaines.challengeplaces.data.database.PlacesDao
import com.juandgaines.challengeplaces.data.database.toPlacesEntity
import com.juandgaines.challengeplaces.utils.providerCities
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import okhttp3.internal.wait
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class E2ETest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var citiesDao: PlacesDao

    @Before
    fun init() {
        hiltRule.inject()

        runBlocking {
            reInsertCities()
        }
    }

    @Test
    fun testE2E() {
        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithContentDescription(
                composeTestRule.activity.getString(R.string.search_screen_description)
            ).fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.getString(R.string.search_field_description)
        ).performTextInput(
            "Al"
        )
        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithContentDescription(
                composeTestRule.activity.getString(R.string.search_screen_list_description)
            ).fetchSemanticsNodes().size == 1
        }
        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.getString(R.string.search_screen_list_description)
        ).onChildren()
            .filter(hasText(
                "Al", substring = true, ignoreCase = true
            ))
            .onFirst()
            .performClick()

        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithContentDescription(
                composeTestRule.activity.getString(R.string.map_screen_marker_description)
            ).fetchSemanticsNodes().size == 1
        }
    }

    private suspend fun reInsertCities() {
        citiesDao.upsertPlaces(providerCities.map { it.toPlacesEntity() })
    }
}