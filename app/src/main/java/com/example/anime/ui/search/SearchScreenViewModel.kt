package com.example.anime.ui.search

import com.example.anime.ui.utils.UiConstants.EMPTY_STRING
import kotlinx.coroutines.flow.MutableStateFlow

class SearchScreenViewModel {
    private val isLoading = MutableStateFlow(false)
    private val searchValue = MutableStateFlow(EMPTY_STRING)

    val searchUiState = SearchUiState(
        searchValue = searchValue,
        isLoading = isLoading,
        onQueryChange = ::onQueryChange
    )

    private fun onQueryChange(query: String) {
        isLoading.value = true
        searchValue.value = if (query.length == 1) query.trim() else query
        isLoading.value = false
    }
}
