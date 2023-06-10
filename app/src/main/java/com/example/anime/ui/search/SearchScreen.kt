package com.example.anime.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.anime.R
import com.example.anime.ui.components.FiltersDrawer
import com.example.anime.ui.navigation.NavItem
import com.example.anime.ui.theme.AnimeAppTheme
import com.example.anime.ui.utils.UiConstants.OPEN_FILTERS
import com.example.anime.ui.utils.UiConstants.THREE_VALUE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                FiltersDrawer(
                    searchUiState = viewModel.searchUiState,
                    closeDrawer = {
                        scope.launch { drawerState.close() }
                    }
                )
            }
        },
        gesturesEnabled = true
    ) {
        SearchScreenContent(
            searchUiState = viewModel.searchUiState,
            openFilterDrawer = { scope.launch { drawerState.open() } }
        ) { id ->
            navHostController.navigate(NavItem.Detail.routeWithArgs(id))
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreenContent(
    searchUiState: SearchUiState,
    openFilterDrawer: () -> Unit,
    navigateToDetails: (Int) -> Unit
) {
    val isLoading by searchUiState.isLoading.collectAsState()
    val searchValue by searchUiState.searchValue.collectAsState()
    val paginatedAnimeProvider by searchUiState.paginatedAnimeProvider.collectAsState()
    val paginatedAnimes = paginatedAnimeProvider?.collectAsLazyPagingItems()
    val favoriteIds by searchUiState.favoriteIds.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { openFilterDrawer() },
                colors = ButtonDefaults.buttonColors(
                    containerColor =
                    MaterialTheme.colorScheme.onBackground
                ),
                elevation = ButtonDefaults.buttonElevation(1.dp),
                shape = ShapeDefaults.Small
            ) {
                Text(text = OPEN_FILTERS)
            }
        }
        Row {
            TextField(
                value = searchValue,
                onValueChange = { value ->
                    searchUiState.onQueryChange(value)
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            searchUiState.onImeActionClick()
                            keyboardController?.hide()
                        },
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    searchUiState.onImeActionClick()
                    keyboardController?.hide()
                }),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    focusedContainerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
        Box(modifier = Modifier.fillMaxSize()) {
            if (isLoading) {
                LoadingScreen()
            } else {
                if (paginatedAnimes != null && paginatedAnimes.itemCount > 0) {
                    LazyVerticalGrid(columns = GridCells.Fixed(THREE_VALUE)) {
                        items(
                            count = paginatedAnimes.itemCount,
                            key = paginatedAnimes.itemKey(),
                            contentType = paginatedAnimes.itemContentType()
                        ) { index ->
                            val item = paginatedAnimes[index]
                            item?.let { resultAnime ->
                                SearchItemCard(
                                    uiAnimeListItem = resultAnime,
                                    favoritesIdsState = favoriteIds,
                                    onFavoriteClick = searchUiState.onFavoriteClick,
                                    navigateToDetails = { navigateToDetails(resultAnime.id) }
                                )
                            }
                        }
                    }
                } else {
                    NoResult()
                }
            }
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

@Composable
fun NoResult() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = stringResource(id = R.string.no_search_results))
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenContentPreview() {
    val searchUiState = SearchUiState(
        isLoading = MutableStateFlow(false),
        searchValue = MutableStateFlow(""),
        favoriteIds = MutableStateFlow(listOf(1)),
        paginatedAnimeProvider = (MutableStateFlow(flowOf())),
        mediaSortFilters = MutableStateFlow(emptyList()),
        typeFilters = MutableStateFlow(emptyList()),
        selectedTypeFilter = MutableStateFlow(null),
        onQueryChange = {},
        onFavoriteClick = {},
        onSortFilterClick = {},
        onTypeFilterClick = {},
        onImeActionClick = {}
    )
    AnimeAppTheme {
        SearchScreenContent(searchUiState = searchUiState, {}, {})
    }
}
