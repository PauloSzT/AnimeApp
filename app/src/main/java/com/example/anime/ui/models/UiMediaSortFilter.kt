package com.example.anime.ui.models

import com.example.anime.type.MediaSort

data class UiMediaSortFilter (
    val filter: UiSortFilter,
    val isSelected: Boolean = false
)
data class UiSortFilter(
   val name: String
)

