package com.example.core.repository.usecases.remote.getanimelistbysearchusecase

import com.example.core.models.CoreMediaType
import com.example.core.models.CoreSearchResultAnime
import com.example.core.models.CoreSortFilter

interface GetAnimeListBySearchUseCase {
    suspend operator fun invoke(
        page: Int,
        query: String,
        typeFilter: CoreMediaType?,
        mediaSort: List<CoreSortFilter>
    ): CoreSearchResultAnime
}
