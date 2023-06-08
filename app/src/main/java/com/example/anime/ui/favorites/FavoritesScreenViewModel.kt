package com.example.anime.ui.favorites

import com.example.anime.ui.models.UiFavoriteAnime
import kotlinx.coroutines.flow.MutableStateFlow

class FavoritesScreenViewModel {

    private val favoriteList = MutableStateFlow(emptyList<UiFavoriteAnime>())

    val favoritesUiState = FavoritesUiState(
        favoriteList = favoriteList
    )
}
