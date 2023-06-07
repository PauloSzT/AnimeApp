package com.example.anime.ui.favorites

import com.example.anime.ui.models.UiAnime
import kotlinx.coroutines.flow.StateFlow

data class FavoritesUiState(
    val favoriteList: StateFlow<List<UiAnime>>
)
