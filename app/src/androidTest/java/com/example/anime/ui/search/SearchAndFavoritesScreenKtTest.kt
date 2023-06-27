package com.example.anime.ui.search

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.IdlingPolicies
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.anime.App
import com.example.anime.MainActivity
import com.example.anime.ui.fetcher.FetchingIdlingResource
import com.example.anime.ui.utils.TestConstants
import com.example.anime.ui.utils.TestConstants.BACK_BTN
import com.example.anime.ui.utils.TestConstants.ITEM_FAVORITE_BTN
import com.example.anime.ui.utils.TestConstants.NAVIGATE_TO_FAVORITE_BTN
import com.example.anime.ui.utils.TestConstants.NO_RESULTS_TEXT
import com.example.anime.ui.utils.TestConstants.RESULTS_LAZY_VERTICAL_GRID
import com.example.anime.ui.utils.TestConstants.SEARCH_TEXT_FIELD
import com.example.anime.ui.utils.UiConstants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class SearchAndFavoritesScreenKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    private val fetchingIdlingResource = FetchingIdlingResource()

    @Before
    fun setUp() {
        composeTestRule.activityRule.scenario.onActivity {
            (it.application as App).setFetcherListener(fetchingIdlingResource)
        }
        IdlingRegistry.getInstance().register(fetchingIdlingResource)
        IdlingPolicies.setIdlingResourceTimeout(30, TimeUnit.SECONDS)
    }

    @Test
    fun viewContainsAHeaderWithATextOfSearch() {
        composeTestRule.onNode(hasText("Search")).apply {
            assertExists()
            assertIsDisplayed()
        }
    }

    @Test
    fun viewContainsAHeaderWithABtnToNavigateToFavorites() {
        composeTestRule.onNodeWithTag(
            NAVIGATE_TO_FAVORITE_BTN,
            useUnmergedTree = true
        ).apply {
            assertExists()
            assertIsDisplayed()
        }
    }

    @Test
    fun viewContainsABtnToOpenFilters() {
        composeTestRule.onNode(hasText(UiConstants.OPEN_FILTERS)).apply {
            assertExists()
            assertIsDisplayed()
        }
    }

    @Test
    fun viewContainsATextFieldToSearchAQuery() {
        composeTestRule.onNodeWithTag(SEARCH_TEXT_FIELD, useUnmergedTree = true)
            .apply {
                assertExists()
                assertIsDisplayed()
            }
    }

    @Test
    fun viewContainsANoResultText() {
        composeTestRule.onNodeWithTag(NO_RESULTS_TEXT, useUnmergedTree = true)
            .apply {
                assertExists()
                assertIsDisplayed()
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun whenUserClicksOnFiltersBtnADrawerIsOpenShowingFilterOptions() =
        runTest {
            composeTestRule.onNode(hasText(UiConstants.OPEN_FILTERS)).performClick()
            delay(2000)
            composeTestRule.onNodeWithText("Section Filters").apply {
                assertExists()
                assertIsDisplayed()
            }
            composeTestRule.onNodeWithText("Type Filters").apply {
                assertExists()
                assertIsDisplayed()
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun whenUserTypesAQueryAndClicksOnSearchIconASearchIsInitiatedAndResultsAreShown() =
        runTest {
            composeTestRule.onNodeWithTag(SEARCH_TEXT_FIELD, useUnmergedTree = true)
                .apply {
                    performClick()
                    delay(1000)
                    performTextInput("Dragon Ball")
                    performImeAction()
                }
            composeTestRule.onNodeWithTag(
                RESULTS_LAZY_VERTICAL_GRID,
                useUnmergedTree = true
            ).apply {
                assertExists()
                assertIsDisplayed()
            }
        }

    @Test
    fun whenUserAddsAFavoriteAnimeAppSavesItInCacheAndDisplaysOnFavoriteScreen() =
        runTest {
            composeTestRule.onNodeWithTag(SEARCH_TEXT_FIELD, useUnmergedTree = true)
                .apply {
                    performClick()
                    delay(1000)
                    performTextInput("Dragon Ball")
                    performImeAction()
                }
            val itemFavoriteBtn =
                composeTestRule.onAllNodesWithTag(ITEM_FAVORITE_BTN, useUnmergedTree = true)
            itemFavoriteBtn[3].performClick()
            composeTestRule.onNodeWithTag(
                TestConstants.NAVIGATE_TO_FAVORITE_BTN,
                useUnmergedTree = true
            ).performClick()
            composeTestRule.onNodeWithText("Gatarou-sensei Arigatou Itsumo Omoshiroi Manga")
            composeTestRule.onNodeWithText("Delete").performClick()
            composeTestRule.onNodeWithText("No Favorites Saved").apply {
                assertExists()
                assertIsDisplayed()
            }
        }

    @Test
    fun whenUserClicksOnBackArrowBtnNavigateBackToSearchScreen() =
        runTest {
            navigateToFavorites()
            composeTestRule.onNodeWithTag(BACK_BTN, useUnmergedTree = true).apply {
                assertExists()
                assertIsDisplayed()
                performClick()
            }
            composeTestRule.onNodeWithTag(NO_RESULTS_TEXT, useUnmergedTree = true)
                .apply {
                    assertExists()
                    assertIsDisplayed()
                }
        }

    private suspend fun navigateToFavorites() {
        composeTestRule.onNodeWithTag(SEARCH_TEXT_FIELD, useUnmergedTree = true)
            .apply {
                performClick()
                delay(1000)
                performTextInput("Dragon Ball")
                performImeAction()
            }
        val itemFavoriteBtn =
            composeTestRule.onAllNodesWithTag(ITEM_FAVORITE_BTN, useUnmergedTree = true)
        itemFavoriteBtn[3].performClick()
        composeTestRule.onNodeWithTag(
            NAVIGATE_TO_FAVORITE_BTN, useUnmergedTree = true
        ).performClick()
    }
}
