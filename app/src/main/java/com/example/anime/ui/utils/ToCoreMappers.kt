package com.example.anime.ui.utils

import com.example.anime.ui.models.UiAnimeListItem
import com.example.anime.ui.models.UiFavoriteAnime
import com.example.anime.ui.models.UiMediaSortFilter
import com.example.anime.ui.models.UiMediaType
import com.example.core.models.CoreFavoriteAnime
import com.example.core.models.CoreMediaType
import com.example.core.models.CoreSortFilter

fun UiMediaType.mapToCoreModel(): CoreMediaType =
    CoreMediaType(name)

fun UiMediaSortFilter.mapToCoreModel(): CoreSortFilter =
    CoreSortFilter(filter.name)

fun UiAnimeListItem.mapToCoreModel(): CoreFavoriteAnime =
    CoreFavoriteAnime(id, title, coverImage)

fun UiFavoriteAnime.mapToCoreModel(): CoreFavoriteAnime =
    CoreFavoriteAnime(id, title, coverImage)
