package com.example.anime.ui.models

import com.example.core.models.CoreAnimeCharacter

data class UiDetailAnime(
    val id: Int,
    val coverImage: String,
    val title: String,
    val episodes: Int,
    val score: Int,
    val genres: List<String?>,
    val englishName: String,
    val japaneseName: String,
    val description: String,
    val characters: List<UiAnimeCharacter>
)
