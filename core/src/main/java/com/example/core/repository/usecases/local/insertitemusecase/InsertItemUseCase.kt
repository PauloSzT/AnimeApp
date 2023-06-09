package com.example.core.repository.usecases.local.insertitemusecase

import com.example.core.models.CoreFavoriteAnime

interface InsertItemUseCase {
    suspend operator fun invoke(item: CoreFavoriteAnime)
}
