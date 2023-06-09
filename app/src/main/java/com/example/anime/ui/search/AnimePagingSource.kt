package com.example.anime.ui.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.anime.ui.models.UiAnimeListItem
import com.example.anime.ui.models.UiSearchResultAnime
import java.io.IOException

class AnimePagingSource(
    val getRemoteList: suspend (Int) -> UiSearchResultAnime
) : PagingSource<Int, UiAnimeListItem>() {

    override fun getRefreshKey(state: PagingState<Int, UiAnimeListItem>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UiAnimeListItem> {
        return try {
            val pageNumber = params.key ?: 1
            val response = getRemoteList(pageNumber)
            LoadResult.Page(
                data = response.animeList,
                null,
                nextKey = if (response.hasNextPage) pageNumber + 1 else null
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }
}
