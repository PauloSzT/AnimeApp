package com.example.data.utils

import com.example.anime.GetAnimeBySearchQuery
import com.example.anime.GetSingleAnimeByIdQuery
import com.example.anime.GetSingleCharacterByIdQuery
import com.example.core.models.CoreAnimeCharacter
import com.example.core.models.CoreAnimeCharacterDetail
import com.example.core.models.CoreAnimeListItem
import com.example.core.models.CoreDetailAnime
import com.example.core.models.CoreFavoriteAnime
import com.example.data.database.AnimeFavoriteEntity

fun GetAnimeBySearchQuery.Medium.mapToCoreModel(): CoreAnimeListItem =
    CoreAnimeListItem(
        id = id,
        title = title?.romaji ?: "",
        coverImage = coverImage?.medium ?: ""
    )

fun GetSingleAnimeByIdQuery.Media.mapToCoreModel(): CoreDetailAnime =
    CoreDetailAnime(
        id = id,
        coverImage = coverImage?.extraLarge ?: "",
        title = title?.romaji ?: "",
        episodes = episodes ?: 0,
        score = averageScore ?: 0,
        genres = genres ?: emptyList(),
        englishName = title?.english ?: "",
        japaneseName = title?.native ?: "",
        description = description ?: "",
        characters = characters?.mapToCoreModel() ?: emptyList()
    )

fun GetSingleAnimeByIdQuery.Characters.mapToCoreModel(): List<CoreAnimeCharacter> =
    nodes?.map { item ->
        CoreAnimeCharacter(
            id = item?.id ?: 0,
            imageUrl = item?.image?.medium ?: "",
            name = item?.name?.full ?: ""
        )
    } ?: emptyList()

fun GetSingleCharacterByIdQuery.Character.mapToCoreModel(): CoreAnimeCharacterDetail =
    CoreAnimeCharacterDetail(
        name = name?.full ?: "",
        imageUrl = image?.large ?: "",
        description = description ?: "",
        gender = gender ?: "",
        dateOfBirth = "${dateOfBirth?.day}-${dateOfBirth?.month}-${dateOfBirth?.year}",
        age = age ?: "",
        bloodType = bloodType ?: ""
    )

fun List<AnimeFavoriteEntity>.mapToCoreModel(): List<CoreFavoriteAnime> = map { animeItem ->
    CoreFavoriteAnime(
        id = animeItem.id,
        title = animeItem.title,
        coverImage = animeItem.coverImage
    )
}
