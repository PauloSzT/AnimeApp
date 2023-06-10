package com.example.data.usecases.local.getallfavoritesusecase

import com.example.core.models.CoreFavoriteAnime
import com.example.core.repository.local.LocalRepository
import com.example.core.repository.usecases.local.getallfavoritesusecase.GetAllFavoritesUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoritesUseCaseImpl @Inject constructor(
    private val localRepository: LocalRepository
): GetAllFavoritesUseCase {
    override fun invoke(): Flow<List<CoreFavoriteAnime>> = localRepository.getAllFavorites()
}
