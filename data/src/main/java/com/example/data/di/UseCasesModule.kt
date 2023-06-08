package com.example.data.di

import com.example.data.usecases.remote.getpaginatedanimeusecase.GetPaginatedAnimeUseCase
import com.example.data.usecases.remote.getpaginatedanimeusecase.GetPaginatedAnimeUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCasesModule {
    @Binds
    abstract fun bindsGetPaginatedAnimeUseCaseWithImpl(
        getPaginatedAnimeUseCaseImpl: GetPaginatedAnimeUseCaseImpl
    ): GetPaginatedAnimeUseCase
}
