package com.example.anime.ui.models

data class UiSearchResultAnime (
    val hasNextPage: Boolean,
    val animeList: List<UiAnimeListItem>
)

data class UiAnimeListItem (
    val id: Int,
    val title: String,
    val coverImage: String,
)
