package com.example.anime.ui.detail

import com.example.anime.ui.models.UiDetailAnime
import kotlinx.coroutines.flow.StateFlow

data class DetailUiState(
    val detailItem: StateFlow<UiDetailAnime?>
)
