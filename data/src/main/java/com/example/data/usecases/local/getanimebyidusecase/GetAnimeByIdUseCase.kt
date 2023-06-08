package com.example.data.usecases.local.getanimebyidusecase

import com.example.data.models.local.AnimeEntity

interface GetAnimeByIdUseCase {
    operator fun invoke(id: String): AnimeEntity
}