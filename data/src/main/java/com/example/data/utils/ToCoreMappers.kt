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
import com.example.data.utils.DataConstants.EMPTY_STRING
import com.example.data.utils.DataConstants.UNDEFINED
import com.example.data.utils.DataConstants.UNKNOWN
import com.example.data.utils.DataConstants.ZERO_VALUE

fun GetAnimeBySearchQuery.Medium.mapToCoreModel(): CoreAnimeListItem =
    CoreAnimeListItem(
        id = id,
        title = title?.romaji ?: EMPTY_STRING,
        coverImage = coverImage?.medium ?: EMPTY_STRING
    )

fun GetSingleAnimeByIdQuery.Media.mapToCoreModel(): CoreDetailAnime =
    CoreDetailAnime(
        id = id,
        coverImage = coverImage?.extraLarge ?: EMPTY_STRING,
        title = title?.romaji ?: UNKNOWN,
        episodes = episodes ?: ZERO_VALUE,
        score = averageScore ?: ZERO_VALUE,
        genres = genres ?: emptyList(),
        englishName = title?.english ?: UNKNOWN,
        japaneseName = title?.native ?: UNKNOWN,
        description = description ?: UNKNOWN,
        characters = characters?.mapToCoreModel() ?: emptyList()
    )

fun GetSingleAnimeByIdQuery.Characters.mapToCoreModel(): List<CoreAnimeCharacter> =
    nodes?.map { item ->
        CoreAnimeCharacter(
            id = item?.id ?: ZERO_VALUE,
            imageUrl = item?.image?.medium ?: EMPTY_STRING,
            name = item?.name?.full ?: EMPTY_STRING
        )
    } ?: emptyList()

fun GetSingleCharacterByIdQuery.Character.mapToCoreModel(): CoreAnimeCharacterDetail =
    CoreAnimeCharacterDetail(
        name = name?.full ?: EMPTY_STRING,
        imageUrl = image?.large ?: EMPTY_STRING,
        description = description ?: UNKNOWN,
        gender = gender ?: UNKNOWN,
        dateOfBirth = "${ dateOfBirth?.day }-${ dateOfBirth?.month }-${ dateOfBirth?.year}",
        age = age ?: UNDEFINED,
        bloodType = bloodType ?: UNKNOWN
    )

fun List<AnimeFavoriteEntity>.mapToCoreModel(): List<CoreFavoriteAnime> = map { animeItem ->
    CoreFavoriteAnime(
        id = animeItem.id,
        title = animeItem.title,
        coverImage = animeItem.coverImage
    )
}
