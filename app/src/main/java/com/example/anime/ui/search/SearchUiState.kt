package com.example.anime.ui.search

import androidx.paging.PagingData
import com.example.anime.GetAnimeBySearchQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

data class SearchUiState(
    val searchValue: StateFlow<String>,
    val isLoading: StateFlow<Boolean>,
    val paginatedAnimeProvider: StateFlow<Flow<PagingData<GetAnimeBySearchQuery.Medium>>?>,
    val onQueryChange: (String) -> Unit,
)
