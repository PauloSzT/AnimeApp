package com.example.core.repository.usecases.local.deleteitemusecase

import com.example.core.models.CoreFavoriteAnime

interface DeleteItemUseCase {
    suspend operator fun invoke(item: CoreFavoriteAnime)
}
