package com.example.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.example.anime.GetAnimeBySearchQuery
import com.example.anime.type.MediaSort
import com.example.anime.type.MediaType
import java.io.IOException

class AnimePagingSource(
    private val apolloClient: ApolloClient,
    private val query: String,
    private val typeFilter: MediaType?,
    private val mediaSort: List<MediaSort>
) : PagingSource<Int, GetAnimeBySearchQuery.Medium>() {

    override fun getRefreshKey(state: PagingState<Int, GetAnimeBySearchQuery.Medium>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetAnimeBySearchQuery.Medium> {
        return try {
            val pageNumber = params.key ?: 1
            val response = apolloClient.query(
                GetAnimeBySearchQuery(
                    Optional.present(pageNumber),
                    Optional.present(10),
                    Optional.present(query),
                    Optional.presentIfNotNull(typeFilter),
                    Optional.present(mediaSort)
                )
            ).execute().data?.Page
            response?.media?.let{ list ->
                LoadResult.Page(
                    data = list.filterNotNull(),
                    null,
                    nextKey = if (response.pageInfo?.hasNextPage == true) pageNumber + 1 else null
                )
            }?: LoadResult.Error(throwable = Throwable("End of Pagination"))
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }
}
