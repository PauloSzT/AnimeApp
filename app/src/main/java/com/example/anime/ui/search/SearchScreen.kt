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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.anime.R
import com.example.anime.ui.components.FiltersDrawer
import com.example.anime.ui.navigation.NavItem
import com.example.anime.ui.utils.UiConstants.OPEN_FILTERS
import com.example.anime.ui.utils.UiConstants.THREE_VALUE
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

@OptIn(ExperimentalMaterial3Api::class)
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
                        modifier = Modifier.clickable { searchUiState.onImeActionClick() },
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {searchUiState.onImeActionClick()} ),
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
                LazyVerticalGrid(columns = GridCells.Fixed(THREE_VALUE)) {
                    paginatedAnimes?.let { animeItem ->
                        items(
                            count = animeItem.itemCount,
                            key = animeItem.itemKey(),
                            contentType = animeItem.itemContentType()
                        ) { index ->
                            val item = animeItem[index]
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

                }
            }
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

@Composable
fun NoResult() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.no_search_results))
    }
}
