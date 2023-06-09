package com.example.data.di

import android.content.Context
import com.example.data.database.AppDataBase
import com.example.data.database.FavoriteAnimeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context): AppDataBase =
        AppDataBase.getDatabase(context)

    @Singleton
    @Provides
    fun provideQuoteDao(database: AppDataBase): FavoriteAnimeDao = database.favoriteQuoteDao()
}
