package com.example.data.di

import com.example.data.repository.local.LocalRepository
import com.example.data.repository.local.LocalRepositoryImpl
import com.example.data.repository.remote.RemoteRepository
import com.example.data.repository.remote.RemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindLocalRepositoryWithImpl(
        localRepositoryImpl: LocalRepositoryImpl
    ): LocalRepository

    @Binds
    abstract fun bindRemoteRepositoryWithImpl(
        remoteRepositoryImpl: RemoteRepositoryImpl
    ): RemoteRepository
}
