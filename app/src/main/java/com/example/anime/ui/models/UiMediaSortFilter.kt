package com.example.anime.ui.models

data class UiMediaSortFilter (
    val filter: UiSortFilter,
    val isSelected: Boolean = false
)
data class UiSortFilter(
   val name: String
)

