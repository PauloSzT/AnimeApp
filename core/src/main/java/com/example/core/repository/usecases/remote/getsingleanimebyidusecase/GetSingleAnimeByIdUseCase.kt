package com.example.core.repository.usecases.remote.getsingleanimebyidusecase

import com.example.core.models.CoreDetailAnime

interface GetSingleAnimeByIdUseCase {
    suspend operator fun invoke(
        animeId: Int
    ): CoreDetailAnime?
}
