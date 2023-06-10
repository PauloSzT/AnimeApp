package com.example.anime.ui.character

import com.example.anime.ui.models.UiAnimeCharacterDetail
import kotlinx.coroutines.flow.StateFlow

data class CharacterUiState(
    val characterItem: StateFlow<UiAnimeCharacterDetail?>
)
