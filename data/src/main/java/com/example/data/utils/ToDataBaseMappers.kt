package com.example.data.utils

import com.example.core.models.CoreFavoriteAnime
import com.example.data.database.AnimeFavoriteEntity

fun CoreFavoriteAnime.mapToDataBaseModel(): AnimeFavoriteEntity =
    AnimeFavoriteEntity(id = id, coverImage = coverImage, title = title)
