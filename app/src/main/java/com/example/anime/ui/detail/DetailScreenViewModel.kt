package com.example.anime.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anime.ui.models.UiDetailAnime
import com.example.anime.ui.utils.mapToUiModel
import com.example.core.repository.usecases.remote.getsingleanimebyidusecase.GetSingleAnimeByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    getSingleAnimeByIdUseCase: GetSingleAnimeByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val detailItem: MutableStateFlow<UiDetailAnime?> = MutableStateFlow(null)

    init {
        viewModelScope.launch {
            detailItem.value = getSingleAnimeByIdUseCase(
                savedStateHandle.get<Int>("animeId") ?: 0
            )?.mapToUiModel()
        }
    }

    val detailUiState = DetailUiState(
        detailItem = detailItem
    )
}
