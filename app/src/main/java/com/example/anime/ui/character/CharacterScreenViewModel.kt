package com.example.anime.ui.character

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anime.ui.models.UiAnimeCharacterDetail
import com.example.anime.ui.utils.mapToUiModel
import com.example.core.repository.usecases.remote.getsinglecharacterbyidusecase.GetSingleCharacterByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterScreenViewModel @Inject constructor(
    getSingleCharacterByIdUseCase: GetSingleCharacterByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val characterItem: MutableStateFlow<UiAnimeCharacterDetail?> = MutableStateFlow(null)

    init {
        viewModelScope.launch {
            characterItem.value = getSingleCharacterByIdUseCase(
                savedStateHandle.get<Int>("characterId") ?: 0
            )?.mapToUiModel()
        }
    }
    val characterUiState = CharacterUiState(
        characterItem = characterItem
    )
}
