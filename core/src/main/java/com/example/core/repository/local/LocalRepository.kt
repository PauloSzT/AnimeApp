package com.example.core.repository.local

import com.example.core.models.CoreFavoriteAnime
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    fun getAllFavorites(): Flow<List<CoreFavoriteAnime>>
    fun getAllIds(): Flow<List<Int>>
    suspend fun insertItem(item: CoreFavoriteAnime)
    suspend fun deleteItem(item: CoreFavoriteAnime)
}
