package com.example.core.repository.usecases.local.getallfavoritesusecase

import com.example.core.models.CoreFavoriteAnime
import kotlinx.coroutines.flow.Flow

interface GetAllFavoritesUseCase {
    operator fun invoke(): Flow<List<CoreFavoriteAnime>>
}
