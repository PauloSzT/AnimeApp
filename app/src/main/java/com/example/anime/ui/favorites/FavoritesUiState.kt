package com.example.anime.ui.favorites

import com.example.anime.ui.models.UiFavoriteAnime
import kotlinx.coroutines.flow.StateFlow

data class FavoritesUiState(
    val favoriteList: StateFlow<List<UiFavoriteAnime>>
)
