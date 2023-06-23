package com.example.anime.ui.favorites

import com.example.anime.ui.models.UiFavoriteAnime
import com.example.anime.ui.utils.mapToCoreModel
import com.example.core.models.CoreFavoriteAnime
import com.example.core.repository.usecases.local.deleteitemusecase.DeleteItemUseCase
import com.example.core.repository.usecases.local.getallfavoritesusecase.GetAllFavoritesUseCase
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class FavoritesScreenViewModelTest {

    private lateinit var viewModel: FavoritesScreenViewModel
    private lateinit var uiState: FavoritesUiState

    private val getAllFavoritesUseCase: GetAllFavoritesUseCase = mockk()

    private val deleteItemUseCase: DeleteItemUseCase = mockk()
    private val testDispatcher = UnconfinedTestDispatcher()

    private val mockedCoreFavoriteAnimeList = listOf(
        CoreFavoriteAnime(
            id = 1,
            title = "TestFavoriteTitle1",
            coverImage = "TestFavoriteCoverImage1"
        ),
        CoreFavoriteAnime(
            id = 2,
            title = "TestFavoriteTitle2",
            coverImage = "TestFavoriteCoverImage2"
        ),
        CoreFavoriteAnime(
            id = 3,
            title = "TestFavoriteTitle3",
            coverImage = "TestFavoriteCoverImage3"
        )
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() = runBlocking {
        Dispatchers.setMain(testDispatcher)

        coEvery { getAllFavoritesUseCase() } returns flowOf(mockedCoreFavoriteAnimeList)
        coEvery { deleteItemUseCase(any()) } just Runs

        viewModel = FavoritesScreenViewModel(
            getAllFavoritesUseCase,
            deleteItemUseCase
        )
        uiState = viewModel.favoritesUiState
    }

    @Test
    fun `onDeleteFavoriteAnimeClick delete item if finds item in favorites list`() =
        runBlocking {
            val collectJob = launch(testDispatcher){
                viewModel.favoritesUiState.favoriteList.collect()
            }
            val newFavoriteItemAnime = UiFavoriteAnime(
                id = 3,
                title = "TestFavoriteTitle3",
                coverImage = "TestFavoriteCoverImage3"
            )
            uiState.onDeleteFavoriteAnimeClick(newFavoriteItemAnime)
            coVerify { deleteItemUseCase(newFavoriteItemAnime.mapToCoreModel()) }
            collectJob.cancel()
        }
}