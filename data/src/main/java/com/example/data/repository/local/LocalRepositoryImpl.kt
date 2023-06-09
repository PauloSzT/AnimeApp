package com.example.data.repository.local

import com.example.core.models.CoreFavoriteAnime
import com.example.core.repository.local.LocalRepository
import com.example.data.database.FavoriteAnimeDao
import com.example.data.utils.mapToCoreModel
import com.example.data.utils.mapToDataBaseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val favoriteAnimeDao: FavoriteAnimeDao
) : LocalRepository {
    override fun getAllFavorites(): Flow<List<CoreFavoriteAnime>> {
        return favoriteAnimeDao.getAllFavorites().map { list -> list.mapToCoreModel() }
    }

    override fun getAllIds(): Flow<List<Int>> {
        return favoriteAnimeDao.getAllIds()
    }

    override suspend fun insertItem(item: CoreFavoriteAnime) {
        return favoriteAnimeDao.insertItem(item.mapToDataBaseModel())
    }

    override suspend fun deleteItem(item: CoreFavoriteAnime) {
        return favoriteAnimeDao.deleteItem(item.mapToDataBaseModel())
    }
}
