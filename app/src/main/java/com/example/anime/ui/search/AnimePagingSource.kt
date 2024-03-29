package com.example.anime.ui.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.anime.ui.models.UiAnimeListItem
import com.example.anime.ui.models.UiSearchResultAnime
import com.example.anime.ui.utils.UiConstants.ONE_VALUE
import java.io.IOException

class AnimePagingSource(
    val stopLoadingState: () -> Unit,
    val getRemoteList: suspend (Int) -> UiSearchResultAnime
) : PagingSource<Int, UiAnimeListItem>() {

    override fun getRefreshKey(state: PagingState<Int, UiAnimeListItem>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(ONE_VALUE)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(ONE_VALUE)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UiAnimeListItem> {
        return try {
            val pageNumber = params.key ?: ONE_VALUE
            val response = getRemoteList(pageNumber)
            stopLoadingState()
            LoadResult.Page(
                data = response.animeList,
                null,
                nextKey = if (response.hasNextPage) pageNumber + ONE_VALUE else null
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }
}
