package com.example.anime.ui.search

import androidx.paging.PagingData
import com.example.anime.ui.models.UiAnimeListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

data class SearchUiState(
    val searchValue: StateFlow<String>,
    val isLoading: StateFlow<Boolean>,
    val favoriteIds: StateFlow<List<Int>>,
    val paginatedAnimeProvider: StateFlow<Flow<PagingData<UiAnimeListItem>>?>,
    val onQueryChange: (String) -> Unit,
    val onFavoriteClick: (UiAnimeListItem) -> Unit
)
