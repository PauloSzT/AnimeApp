package com.example.data.repository.remote

import GetAnimeBySearchQuery
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import type.MediaSort
import type.MediaType

interface RemoteRepository {
    fun getPaginatedAnime(
        query: String,
        typeFilter: MediaType,
        mediaSort: List<MediaSort>
    ): Flow<PagingData<GetAnimeBySearchQuery.Medium>>
}
