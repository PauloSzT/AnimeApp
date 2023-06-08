package com.example.data.usecases.local.getanimebyidusecase

import com.example.data.models.local.AnimeEntity
import com.example.data.repository.local.LocalRepository
import javax.inject.Inject

class GetAnimeByIdUseCaseImpl @Inject constructor(private val localRepository: LocalRepository) :
    GetAnimeByIdUseCase {
    override operator fun invoke(id: String): AnimeEntity {
        return localRepository.getAnimeById(id)
    }
}