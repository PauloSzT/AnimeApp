package com.example.anime.ui.detail

import androidx.lifecycle.SavedStateHandle
import com.example.anime.ui.utils.mapToUiModel
import com.example.core.models.CoreAnimeCharacter
import com.example.core.models.CoreDetailAnime
import com.example.core.repository.usecases.remote.getsingleanimebyidusecase.GetSingleAnimeByIdUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DetailScreenViewModelTest {

    private lateinit var viewModel: DetailScreenViewModel
    private lateinit var uiState: DetailUiState

    private val getSingleAnimeByIdUseCase: GetSingleAnimeByIdUseCase = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()
    private val mockedDetailItem = CoreDetailAnime(
        id = 1,
        coverImage = "TestCoverImage",
        title = "TestTitle",
        episodes = 10,
        score = 100,
        genres = listOf("TestGenres", "TestGenres2"),
        englishName = "TestEnglishName",
        japaneseName = "TestJapaneseName",
        description = "TestDescritpion",
        characters = listOf
            (
            CoreAnimeCharacter(
                id = 1,
                imageUrl = "TestImageUrl",
                name = "TestAnimeCharacterName"
            ),
        )
    )

    private val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() = runBlocking {
        Dispatchers.setMain(testDispatcher)

        coEvery { getSingleAnimeByIdUseCase(any()) } returns mockedDetailItem
        coEvery { savedStateHandle.get<Int>(any()) } returns 1

        viewModel = DetailScreenViewModel(
            getSingleAnimeByIdUseCase,
            savedStateHandle
        )
        uiState = viewModel.detailUiState
    }

    @Test
    fun `DetailItem is loaded correctly`() =
        runTest {
            val collectJob = launch(testDispatcher) {
                viewModel.detailUiState.detailItem.collect()
            }
            val newDetailItem = viewModel.detailUiState.detailItem.first()
            assertEquals(mockedDetailItem.mapToUiModel(), newDetailItem)
            collectJob.cancel()
        }
}
