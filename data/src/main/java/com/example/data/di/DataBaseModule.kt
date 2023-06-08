package com.example.data.di

import com.example.data.usecases.local.getanimebyidusecase.GetAnimeByIdUseCase
import com.example.data.usecases.local.getanimebyidusecase.GetAnimeByIdUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBaseModule {
    @Binds
    abstract fun getAnimeById(implementation: GetAnimeByIdUseCaseImpl): GetAnimeByIdUseCase
}
