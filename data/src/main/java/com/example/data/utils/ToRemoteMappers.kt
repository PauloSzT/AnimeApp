package com.example.data.utils

import com.example.anime.type.MediaSort
import com.example.anime.type.MediaType
import com.example.core.models.CoreMediaType
import com.example.core.models.CoreSortFilter

fun CoreMediaType.mapToRemoteModel(): MediaType? {
    return MediaType.values().find { name == it.name }
}

fun CoreSortFilter.mapToRemoteModel(): MediaSort? {
    return MediaSort.values().find { name == it.name }
}
