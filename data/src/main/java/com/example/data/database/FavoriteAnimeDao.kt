package com.example.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteAnimeDao {
    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): Flow<List<AnimeFavoriteEntity>>

    @Query("SELECT id FROM favorite")
    fun getAllIds(): Flow<List<Int>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: AnimeFavoriteEntity)

    @Delete
    suspend fun deleteItem(item: AnimeFavoriteEntity)
}
