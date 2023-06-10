package com.example.anime.ui.utils

import com.example.anime.type.MediaSort
import com.example.anime.type.MediaType
import com.example.anime.ui.models.UiAnimeCharacter
import com.example.anime.ui.models.UiAnimeCharacterDetail
import com.example.anime.ui.models.UiAnimeListItem
import com.example.anime.ui.models.UiDetailAnime
import com.example.anime.ui.models.UiFavoriteAnime
import com.example.anime.ui.models.UiMediaType
import com.example.anime.ui.models.UiSearchResultAnime
import com.example.anime.ui.models.UiSortFilter
import com.example.core.models.CoreAnimeCharacter
import com.example.core.models.CoreAnimeCharacterDetail
import com.example.core.models.CoreAnimeListItem
import com.example.core.models.CoreDetailAnime
import com.example.core.models.CoreFavoriteAnime
import com.example.core.models.CoreSearchResultAnime

fun CoreSearchResultAnime.mapToUiModel(): UiSearchResultAnime =
    UiSearchResultAnime(
        hasNextPage,
        animeList.map { item -> item.mapToUiModel() }
    )

fun CoreAnimeListItem.mapToUiModel(): UiAnimeListItem =
    UiAnimeListItem(id, title, coverImage)

fun MediaSort.mapToUiModel(): UiSortFilter =
    UiSortFilter(name)

fun MediaType.mapToUiModel() : UiMediaType =
    UiMediaType(name)

fun CoreDetailAnime.mapToUiModel(): UiDetailAnime =
    UiDetailAnime(
        id = id,
        coverImage = coverImage,
        title = title,
        episodes = episodes,
        score = score,
        genres = genres,
        englishName = englishName,
        japaneseName = japaneseName,
        description = description,
        characters = characters.map { character ->
            character.mapToUiModel()
        }
    )

fun CoreAnimeCharacter.mapToUiModel(): UiAnimeCharacter =
    UiAnimeCharacter(
        id = id,
        imageUrl = imageUrl,
        name = name
    )

fun CoreAnimeCharacterDetail.mapToUiModel(): UiAnimeCharacterDetail =
    UiAnimeCharacterDetail(
        name = name,
        imageUrl = imageUrl,
        description = description,
        gender = gender,
        dateOfBirth = dateOfBirth,
        age = age,
        bloodType = bloodType
    )

fun CoreFavoriteAnime.mapToUiModel(): UiFavoriteAnime =
    UiFavoriteAnime(
        id = id,
        title = title,
        coverImage = coverImage
    )
