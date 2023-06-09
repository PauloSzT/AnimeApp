package com.example.data.usecases.local.insertitemusecase

import com.example.core.models.CoreFavoriteAnime
import com.example.core.repository.local.LocalRepository
import com.example.core.repository.usecases.local.insertitemusecase.InsertItemUseCase
import javax.inject.Inject

class InsertItemUseCaseImpl @Inject constructor(
    private val localRepository: LocalRepository
): InsertItemUseCase {
    override suspend fun invoke(item: CoreFavoriteAnime) = localRepository.insertItem(item)
}
