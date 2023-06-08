package com.example.data.repository.remote

import GetAnimeBySearchQuery
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo3.ApolloClient
import com.example.data.network.AnimePagingSource
import kotlinx.coroutines.flow.Flow
import type.MediaSort
import type.MediaType
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val apolloClient: ApolloClient) :
    RemoteRepository {
    override fun getPaginatedAnime(
        query: String,
        typeFilter: MediaType,
        mediaSort: List<MediaSort>
    ): Flow<PagingData<GetAnimeBySearchQuery.Medium>> = Pager(
        initialKey = null,
        config = PagingConfig(
            pageSize = 50,
            enablePlaceholders = false,
            prefetchDistance = 1
        ),
        pagingSourceFactory = {
            AnimePagingSource(
                apolloClient = apolloClient,
                query = query,
                typeFilter = typeFilter,
                mediaSort = mediaSort
            )
        }
    ).flow
}
