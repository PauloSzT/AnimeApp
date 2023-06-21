package com.example.data.repository.remote

import com.example.core.models.CoreAnimeCharacterDetail
import com.example.core.models.CoreDetailAnime
import com.example.core.models.CoreMediaType
import com.example.core.models.CoreSearchResultAnime
import com.example.core.models.CoreSortFilter
import com.example.core.remote.RemoteService
import com.example.core.repository.remote.RemoteRepository
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val remoteService: RemoteService) :
    RemoteRepository {

    override suspend fun getAnimeListBySearch(
        page: Int,
        query: String,
        typeFilter: CoreMediaType?,
        mediaSort: List<CoreSortFilter>
    ): CoreSearchResultAnime {
        return remoteService.getAnimeListBySearch(page, query, typeFilter, mediaSort)
    }

    override suspend fun getAnimeById(animeId: Int): CoreDetailAnime? {
        return remoteService.getAnimeById(animeId)
    }

    override suspend fun getCharacterById(characterId: Int): CoreAnimeCharacterDetail? {
        return remoteService.getCharacterById(characterId)
    }
}
