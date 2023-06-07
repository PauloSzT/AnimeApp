package com.example.anime.ui.favorites

import com.example.anime.ui.models.UiAnime
import kotlinx.coroutines.flow.MutableStateFlow

class FavoritesScreenViewModel {

    private val favoriteList = MutableStateFlow(emptyList<UiAnime>())

    val favoritesUiState = FavoritesUiState(
        favoriteList = favoriteList
    )
}
