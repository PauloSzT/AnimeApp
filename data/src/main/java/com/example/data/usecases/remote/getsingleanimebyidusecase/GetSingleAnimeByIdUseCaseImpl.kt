package com.example.data.usecases.remote.getsingleanimebyidusecase

import com.example.core.models.CoreDetailAnime
import com.example.core.repository.remote.RemoteRepository
import com.example.core.repository.usecases.remote.getsingleanimebyidusecase.GetSingleAnimeByIdUseCase
import javax.inject.Inject

class GetSingleAnimeByIdUseCaseImpl @Inject constructor(
    private val remoteRepository: RemoteRepository
) : GetSingleAnimeByIdUseCase {
    override suspend operator fun invoke(animeId: Int): CoreDetailAnime? =
        remoteRepository.getAnimeById(animeId)
}
