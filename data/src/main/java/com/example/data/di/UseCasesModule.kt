package com.example.data.di

import com.example.core.repository.usecases.local.deleteitemusecase.DeleteItemUseCase
import com.example.core.repository.usecases.local.getallfavoritesusecase.GetAllFavoritesUseCase
import com.example.core.repository.usecases.local.getallidsusecase.GetAllIdsUseCase
import com.example.core.repository.usecases.local.insertitemusecase.InsertItemUseCase
import com.example.core.repository.usecases.remote.getanimelistbysearchusecase.GetAnimeListBySearchUseCase
import com.example.core.repository.usecases.remote.getsingleanimebyidusecase.GetSingleAnimeByIdUseCase
import com.example.core.repository.usecases.remote.getsinglecharacterbyidusecase.GetSingleCharacterByIdUseCase
import com.example.data.usecases.local.deleteitemusecase.DeleteItemUseCaseImpl
import com.example.data.usecases.local.getallfavoritesusecase.GetAllFavoritesUseCaseImpl
import com.example.data.usecases.local.getallidsusecase.GetAllIdsUseCaseImpl
import com.example.data.usecases.local.insertitemusecase.InsertItemUseCaseImpl
import com.example.data.usecases.remote.getanimelistbysearchusecase.GetAnimeListBySearchUseCaseImpl
import com.example.data.usecases.remote.getsingleanimebyidusecase.GetSingleAnimeByIdUseCaseImpl
import com.example.data.usecases.remote.getsinglecharacterbyidusecase.GetSingleCharacterByIdUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCasesModule {
    @Binds
    abstract fun bindsGetAnimeListBySearchUseCaseWithImpl(
        getAnimeListBySearchUseCaseImpl: GetAnimeListBySearchUseCaseImpl
    ): GetAnimeListBySearchUseCase

    @Binds
    abstract fun bindsGetSingleAnimeByIdUseCaseWithImpl(
        getSingleAnimeByIdUseCaseImpl: GetSingleAnimeByIdUseCaseImpl
    ): GetSingleAnimeByIdUseCase

    @Binds
    abstract fun bindsGetSingleCharacterByIdUseCaseWithImpl(
        getSingleCharacterByIdUseCaseImpl: GetSingleCharacterByIdUseCaseImpl
    ): GetSingleCharacterByIdUseCase

    @Binds
    abstract fun bindsDeleteItemUseCaseWithImpl(
        deleteItemUseCaseImpl: DeleteItemUseCaseImpl
    ): DeleteItemUseCase

    @Binds
    abstract fun bindsGetAllFavoritesUseCaseWithImpl(
        getAllFavoritesUseCaseImpl: GetAllFavoritesUseCaseImpl
    ): GetAllFavoritesUseCase

    @Binds
    abstract fun bindsGetAllIdsUseCaseWithImpl(
        getAllIdsUseCaseImpl: GetAllIdsUseCaseImpl
    ): GetAllIdsUseCase

    @Binds
    abstract fun bindsInsertItemUseCaseWithImpl(
        insertItemUseCaseImpl: InsertItemUseCaseImpl
    ): InsertItemUseCase
}
