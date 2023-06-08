package com.example.anime.ui.models

import com.example.anime.type.MediaSort

data class MediaSortFilter (
    val filter: MediaSort,
    val isSelected: Boolean = false
)
