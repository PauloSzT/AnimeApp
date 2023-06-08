package com.example.anime.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anime.type.MediaSort
import com.example.anime.type.MediaType
import com.example.anime.ui.models.MediaSortFilter
import com.example.anime.ui.utils.UiConstants.EMPTY_STRING
import com.example.data.usecases.remote.getpaginatedanimeusecase.GetPaginatedAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    getPaginatedAnimeUseCase: GetPaginatedAnimeUseCase,
) : ViewModel() {
    private val isLoading = MutableStateFlow(false)
    private val searchValue = MutableStateFlow(EMPTY_STRING)
    private val mediaSortFilters = MutableStateFlow(MediaSort.values().map { item ->
        MediaSortFilter(item)
    })
    private val typeFilters = MutableStateFlow<MediaType?>(null)
    private val paginatedAnimeProvider = combine(
        mediaSortFilters,
        searchValue,
        typeFilters
    ) { sortFilters, query, typeFilter ->
        getPaginatedAnimeUseCase.invoke(
            query,
            typeFilter,
            sortFilters.filter { item -> item.isSelected }.map { item -> item.filter })
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    val searchUiState = SearchUiState(
        searchValue = searchValue,
        isLoading = isLoading,
        paginatedAnimeProvider = paginatedAnimeProvider,
        onQueryChange = ::onQueryChange
    )

    private fun onQueryChange(query: String) {
        isLoading.value = true
        searchValue.value = if (query.length == 1) query.trim() else query
        isLoading.value = false
    }
}
