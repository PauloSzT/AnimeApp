package com.example.anime.ui.search

import kotlinx.coroutines.flow.StateFlow

data class SearchUiState(
    val searchValue: StateFlow<String>,
    val isLoading: StateFlow<Boolean>,
    val onQueryChange: (String)-> Unit
)
