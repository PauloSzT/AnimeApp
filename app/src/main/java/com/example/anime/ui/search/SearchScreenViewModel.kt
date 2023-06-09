package com.example.anime.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.anime.type.MediaSort
import com.example.anime.type.MediaType
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
    private val mediaSortFilters = MutableStateFlow(MediaSort.values().mapNotNull { item ->
        if (item.rawValue == "UNKNOWN__") null else UiMediaSortFilter(item.mapToUiModel())
    })
    private val typeFilters = MutableStateFlow(MediaType.values().mapNotNull { item ->
        if (item.rawValue == "UNKNOWN__") null else item.mapToUiModel()
    })
    private val selectedTypeFilter = MutableStateFlow<UiMediaType?>(null)
    private val favoriteIds =
        getAllIdsUseCase().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    private val paginatedAnimeProvider = combine(
        mediaSortFilters,
        searchValue,
        selectedTypeFilter
    ) { sortFilters, query, typeFilter ->
        Pager(
            initialKey = null,
            config = PagingConfig(
                pageSize = 50,
                enablePlaceholders = false,
                prefetchDistance = 1
            ),
            pagingSourceFactory = {
                AnimePagingSource({ isLoading.value = false }) { page ->
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
        mediaSortFilters = mediaSortFilters,
        typeFilters = typeFilters,
        selectedTypeFilter = selectedTypeFilter,
        onQueryChange = ::onQueryChange,
        onFavoriteClick = ::onFavoriteClick,
        onSortFilterClick = ::onSortFilterClick,
        onTypeFilterClick = ::onTypeFilterClick
    )

    private fun onQueryChange(query: String) {
        isLoading.value = true
        searchValue.value = if (query.length == 1) query.trim() else query
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

    private fun onTypeFilterClick(filter: UiMediaType) {
        isLoading.value = true
        if (filter.name == selectedTypeFilter.value?.name) selectedTypeFilter.value = null else selectedTypeFilter.value =
            filter
    }

    private fun onSortFilterClick(filter: UiMediaSortFilter) {
        isLoading.value = true
        mediaSortFilters.value = mediaSortFilters.value.map { item ->
            if (filter.filter.name == item.filter.name) item.copy(isSelected = !item.isSelected) else item
        }
    }
}
