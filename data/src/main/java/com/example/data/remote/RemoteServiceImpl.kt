package com.example.data.remote

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.example.anime.GetAnimeBySearchQuery
import com.example.anime.GetSingleAnimeByIdQuery
import com.example.anime.GetSingleCharacterByIdQuery
import com.example.core.models.CoreAnimeCharacterDetail
import com.example.core.models.CoreDetailAnime
import com.example.core.models.CoreMediaType
import com.example.core.models.CoreSearchResultAnime
import com.example.core.models.CoreSortFilter
import com.example.core.remote.RemoteService
import com.example.data.utils.DataConstants
import com.example.data.utils.mapToCoreModel
import com.example.data.utils.mapToRemoteModel
import javax.inject.Inject


class RemoteServiceImpl @Inject constructor(private val apolloClient: ApolloClient) :
    RemoteService {

    override suspend fun getAnimeListBySearch(
        page: Int,
        query: String,
        typeFilter: CoreMediaType?,
        mediaSort: List<CoreSortFilter>
    ): CoreSearchResultAnime {
        val response = apolloClient.query(
            GetAnimeBySearchQuery(
                page = Optional.present(page),
                perPage = Optional.present(DataConstants.TEN_VALUE),
                query = Optional.present(query),
                mediaType = Optional.presentIfNotNull(typeFilter?.mapToRemoteModel()),
                mediaSort = Optional.present(mediaSort.map { item -> item.mapToRemoteModel() })
            )
        ).execute().data?.Page
        return CoreSearchResultAnime(
            hasNextPage = response?.pageInfo?.hasNextPage ?: false,
            animeList = response?.media?.mapNotNull { item -> item?.mapToCoreModel() }
                ?: emptyList()
        )
    }

    override suspend fun getAnimeById(animeId: Int): CoreDetailAnime? {
        return apolloClient.query(
            GetSingleAnimeByIdQuery(
                Optional.present(animeId)
            )
        ).execute().data?.Media?.mapToCoreModel()
    }

    override suspend fun getCharacterById(characterId: Int): CoreAnimeCharacterDetail? {
        return apolloClient.query(
            GetSingleCharacterByIdQuery(
                Optional.present(characterId)
            )
        ).execute().data?.Character?.mapToCoreModel()
    }
}
