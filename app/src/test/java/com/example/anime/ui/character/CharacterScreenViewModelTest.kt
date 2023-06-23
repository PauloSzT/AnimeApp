package com.example.anime.ui.character

import androidx.lifecycle.SavedStateHandle
import com.example.anime.ui.utils.mapToUiModel
import com.example.core.models.CoreAnimeCharacterDetail
import com.example.core.repository.usecases.remote.getsinglecharacterbyidusecase.GetSingleCharacterByIdUseCase
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

class CharacterScreenViewModelTest {

    private lateinit var viewModel: CharacterScreenViewModel
    private lateinit var uiState: CharacterUiState

    private val getSingleCharacterByIdUseCase: GetSingleCharacterByIdUseCase = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()

    private val mockedCharacterItem = CoreAnimeCharacterDetail(
        name = "TestTitle",
        imageUrl = "TestImageUrl",
        description = "TestDescription",
        gender = "TestGender",
        dateOfBirth = "TestDateOfBirth",
        age = "TestAge",
        bloodType = "TestBloodType"
    )

    private val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() = runBlocking {
        Dispatchers.setMain(testDispatcher)

        coEvery { getSingleCharacterByIdUseCase(any()) } returns mockedCharacterItem
        coEvery { savedStateHandle.get<Int>(any()) } returns 1

        viewModel = CharacterScreenViewModel(
            getSingleCharacterByIdUseCase,
            savedStateHandle
        )
        uiState = viewModel.characterUiState
    }

    @Test
    fun `CharacterItem is loaded correctly`() =
        runTest {
            val collectJob = launch(testDispatcher) {
                viewModel.characterUiState.characterItem.collect()
            }
            val newCharacterItem = viewModel.characterUiState.characterItem.first()
            assertEquals(mockedCharacterItem.mapToUiModel(), newCharacterItem)
            collectJob.cancel()
        }
}
