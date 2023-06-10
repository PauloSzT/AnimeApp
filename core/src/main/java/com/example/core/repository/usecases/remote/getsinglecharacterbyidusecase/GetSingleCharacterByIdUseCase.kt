package com.example.core.repository.usecases.remote.getsinglecharacterbyidusecase

import com.example.core.models.CoreAnimeCharacterDetail

interface GetSingleCharacterByIdUseCase {
    suspend operator fun invoke(
        characterId: Int
    ):CoreAnimeCharacterDetail?
}
