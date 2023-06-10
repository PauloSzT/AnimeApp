package com.example.data.usecases.remote.getsinglecharacterbyidusecase

import com.example.core.models.CoreAnimeCharacterDetail
import com.example.core.repository.remote.RemoteRepository
import com.example.core.repository.usecases.remote.getsinglecharacterbyidusecase.GetSingleCharacterByIdUseCase
import javax.inject.Inject

class GetSingleCharacterByIdUseCaseImpl @Inject constructor(
    private val remoteRepository: RemoteRepository
) : GetSingleCharacterByIdUseCase {
    override suspend operator fun invoke(characterId: Int): CoreAnimeCharacterDetail? =
        remoteRepository.getCharacterById(characterId)
}
