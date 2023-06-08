package com.example.data.usecases.remote.getpaginatedanimeusecase

import androidx.paging.PagingData
import com.example.anime.GetAnimeBySearchQuery
import com.example.anime.type.MediaSort
import com.example.anime.type.MediaType
import com.example.data.repository.remote.RemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPaginatedAnimeUseCaseImpl @Inject constructor(
    private val remoteRepository: RemoteRepository
) : GetPaginatedAnimeUseCase {
    override operator fun invoke(
        query: String,
        typeFilter: MediaType?,
        mediaSort: List<MediaSort>
    ): Flow<PagingData<GetAnimeBySearchQuery.Medium>> = remoteRepository.getPaginatedAnime(
        query = query,
        typeFilter = typeFilter,
        mediaSort = mediaSort
    )
}
