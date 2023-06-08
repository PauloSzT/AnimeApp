package com.example.data.repository.remote

import androidx.paging.PagingData
import com.example.anime.GetAnimeBySearchQuery
import com.example.anime.type.MediaSort
import com.example.anime.type.MediaType
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {
    fun getPaginatedAnime(
        query: String,
        typeFilter: MediaType?,
        mediaSort: List<MediaSort>
    ): Flow<PagingData<GetAnimeBySearchQuery.Medium>>
}
