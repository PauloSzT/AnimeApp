package com.example.data.usecases.local.deleteitemusecase

import com.example.core.models.CoreFavoriteAnime
import com.example.core.repository.local.LocalRepository
import com.example.core.repository.usecases.local.deleteitemusecase.DeleteItemUseCase
import javax.inject.Inject

class DeleteItemUseCaseImpl @Inject constructor(
    private val localRepository: LocalRepository
): DeleteItemUseCase {
    override suspend fun invoke(item: CoreFavoriteAnime) = localRepository.deleteItem(item)
}
