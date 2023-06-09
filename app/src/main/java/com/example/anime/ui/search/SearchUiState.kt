package com.example.anime.ui.search

import androidx.paging.PagingData
import com.example.anime.ui.models.UiAnimeListItem
import com.example.anime.ui.models.UiMediaSortFilter
import com.example.anime.ui.models.UiMediaType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

data class SearchUiState(
    val searchValue: StateFlow<String>,
    val isLoading: StateFlow<Boolean>,
    val favoriteIds: StateFlow<List<Int>>,
    val paginatedAnimeProvider: StateFlow<Flow<PagingData<UiAnimeListItem>>?>,
    val mediaSortFilters: StateFlow<List<UiMediaSortFilter>>,
    val typeFilters: StateFlow<List<UiMediaType>>,
    val selectedTypeFilter: StateFlow<UiMediaType?>,
    val onQueryChange: (String) -> Unit,
    val onFavoriteClick: (UiAnimeListItem) -> Unit,
    val onSortFilterClick: (UiMediaSortFilter) -> Unit,
    val onTypeFilterClick: (UiMediaType) -> Unit,
    val onImeActionClick: () -> Unit
)
