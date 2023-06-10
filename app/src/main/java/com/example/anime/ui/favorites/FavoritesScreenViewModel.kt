package com.example.anime.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anime.ui.models.UiFavoriteAnime
import com.example.anime.ui.utils.mapToCoreModel
import com.example.anime.ui.utils.mapToUiModel
import com.example.core.repository.usecases.local.deleteitemusecase.DeleteItemUseCase
import com.example.core.repository.usecases.local.getallfavoritesusecase.GetAllFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesScreenViewModel @Inject constructor(
    getAllFavoritesUseCase: GetAllFavoritesUseCase,
    private val deleteItemUseCase: DeleteItemUseCase
) : ViewModel() {

    private val favoriteList =
        getAllFavoritesUseCase().map { list -> list.map { item -> item.mapToUiModel() } }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val favoritesUiState = FavoritesUiState(
        favoriteList = favoriteList,
        onDeleteFavoriteAnimeClick = ::onDeleteFavoriteAnimeClick
    )

    private fun onDeleteFavoriteAnimeClick(item: UiFavoriteAnime){
        viewModelScope.launch {
            deleteItemUseCase(item.mapToCoreModel())
        }
    }
}
