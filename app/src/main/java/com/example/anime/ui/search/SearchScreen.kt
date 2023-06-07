package com.example.anime.ui.search

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.anime.R
import com.example.anime.ui.navigation.NavItem

@Composable
fun SearchScreen (
    viewModel: SearchScreenViewModel,
    navHostController: NavHostController
){
    SearchScreenContent(searchUiState = viewModel.searchUiState){item->
        navHostController.navigate(NavItem.Detail.routeWithArgs(item))
    }
}


@Composable
fun SearchScreenContent(
    searchUiState: SearchUiState,
    navigateToDetails: (String) -> Unit
) {
    val isLoading by searchUiState.isLoading.collectAsState()
    val searchValue by searchUiState.searchValue.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row{
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = searchValue,
                onValueChange = { value ->
                    searchUiState.onQueryChange(value)
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    focusedContainerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
        Box(modifier = Modifier.fillMaxSize()){
            if(isLoading){
                LoadingScreen()
            } else{
                LazyVerticalGrid(columns = GridCells.Fixed(3)){
//                    SearchItemRow(
//                        uiAnime = item,
//                        favoritesIdsState = ,
//                        onFavoriteClick = {},
//                        navigateToDetails = {navigateToDetails}
//                    )
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
