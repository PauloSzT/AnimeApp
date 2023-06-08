package com.example.anime.ui.utils

import com.example.anime.GetAnimeBySearchQuery
import com.example.anime.ui.models.UiSearchResultAnime

fun GetAnimeBySearchQuery.Medium.mapToSearchUiModel(): UiSearchResultAnime = UiSearchResultAnime(
    id = id,
    title = title?.romaji ?: "",
    coverImage = coverImage?.medium ?: ""
)
