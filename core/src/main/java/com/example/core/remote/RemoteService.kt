package com.example.core.remote

import com.example.core.models.CoreAnimeCharacterDetail
import com.example.core.models.CoreDetailAnime
import com.example.core.models.CoreMediaType
import com.example.core.models.CoreSearchResultAnime
import com.example.core.models.CoreSortFilter

interface RemoteService{
    suspend fun getAnimeListBySearch(
        page:Int,
        query: String,
        typeFilter: CoreMediaType?,
        mediaSort: List<CoreSortFilter>
    ): CoreSearchResultAnime

    suspend fun getAnimeById(
        animeId: Int
    ): CoreDetailAnime?

    suspend fun getCharacterById(
        characterId: Int
    ): CoreAnimeCharacterDetail?
}
