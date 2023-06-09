package com.example.anime.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.anime.type.MediaSort
import com.example.anime.ui.models.UiAnimeListItem
import com.example.anime.ui.models.UiMediaSortFilter
import com.example.anime.ui.models.UiMediaType
import com.example.anime.ui.utils.UiConstants.EMPTY_STRING
import com.example.anime.ui.utils.mapToCoreModel
import com.example.anime.ui.utils.mapToUiModel
import com.example.core.repository.usecases.local.deleteitemusecase.DeleteItemUseCase
import com.example.core.repository.usecases.local.getallidsusecase.GetAllIdsUseCase
import com.example.core.repository.usecases.local.insertitemusecase.InsertItemUseCase
import com.example.core.repository.usecases.remote.getanimelistbysearchusecase.GetAnimeListBySearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    getAnimeListBySearchUseCase: GetAnimeListBySearchUseCase,
    getAllIdsUseCase: GetAllIdsUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val insertItemUseCase: InsertItemUseCase
) : ViewModel() {
    private val isLoading = MutableStateFlow(false)
    private val searchValue = MutableStateFlow(EMPTY_STRING)
    private val mediaSortFilters = MutableStateFlow(MediaSort.values().map { item ->
        UiMediaSortFilter(item.mapToUiModel())
    })
    private val typeFilters = MutableStateFlow<UiMediaType?>(null)
    private val favoriteIds =
        getAllIdsUseCase().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    private val paginatedAnimeProvider = combine(
        mediaSortFilters,
        searchValue,
        typeFilters
    ) { sortFilters, query, typeFilter ->
        Pager(
            initialKey = null,
            config = PagingConfig(
                pageSize = 50,
                enablePlaceholders = false,
                prefetchDistance = 1
            ),
            pagingSourceFactory = {
                AnimePagingSource { page ->
                    getAnimeListBySearchUseCase(
                        page,
                        query,
                        typeFilter?.mapToCoreModel(),
                        sortFilters.mapNotNull { item -> if (item.isSelected) item.mapToCoreModel() else null }
                    ).mapToUiModel()
                }
            }
        ).flow
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    val searchUiState = SearchUiState(
        searchValue = searchValue,
        isLoading = isLoading,
        favoriteIds = favoriteIds,
        paginatedAnimeProvider = paginatedAnimeProvider,
        onQueryChange = ::onQueryChange,
        onFavoriteClick = ::onFavoriteClick
    )

    private fun onQueryChange(query: String) {
        isLoading.value = true
        searchValue.value = if (query.length == 1) query.trim() else query
        isLoading.value = false
    }

    private fun onFavoriteClick(item: UiAnimeListItem) {
        viewModelScope.launch {
            if (favoriteIds.value.contains(item.id)) {
                deleteItemUseCase(item.mapToCoreModel())
            } else {
                insertItemUseCase(item.mapToCoreModel())
            }
        }
    }
}
