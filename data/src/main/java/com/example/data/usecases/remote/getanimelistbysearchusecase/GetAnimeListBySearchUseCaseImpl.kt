package com.example.data.usecases.remote.getanimelistbysearchusecase

import com.example.core.models.CoreMediaType
import com.example.core.models.CoreSearchResultAnime
import com.example.core.models.CoreSortFilter
import com.example.core.repository.remote.RemoteRepository
import com.example.core.repository.usecases.remote.getanimelistbysearchusecase.GetAnimeListBySearchUseCase
import javax.inject.Inject

class GetAnimeListBySearchUseCaseImpl @Inject constructor(
    private val remoteRepository: RemoteRepository
) : GetAnimeListBySearchUseCase {
    override suspend operator fun invoke(
        page: Int,
        query: String,
        typeFilter: CoreMediaType?,
        mediaSort: List<CoreSortFilter>
    ): CoreSearchResultAnime = remoteRepository.getAnimeListBySearch(
        page = page,
        query = query,
        typeFilter = typeFilter,
        mediaSort = mediaSort
    )
}
