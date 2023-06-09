package com.example.core.models

data class CoreSearchResultAnime (
    val hasNextPage: Boolean,
    val animeList: List<CoreAnimeListItem>
)

data class CoreAnimeListItem (
    val id: Int,
    val title: String,
    val coverImage: String,
)
