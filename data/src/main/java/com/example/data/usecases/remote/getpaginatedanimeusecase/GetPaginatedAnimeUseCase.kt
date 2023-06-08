package com.example.data.usecases.remote.getpaginatedanimeusecase

import androidx.paging.PagingData
import com.example.anime.GetAnimeBySearchQuery
import com.example.anime.type.MediaSort
import com.example.anime.type.MediaType
import kotlinx.coroutines.flow.Flow

interface GetPaginatedAnimeUseCase {
    operator fun invoke(
        query: String,
        typeFilter: MediaType?,
        mediaSort: List<MediaSort>
    ): Flow<PagingData<GetAnimeBySearchQuery.Medium>>
}
