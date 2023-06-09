package com.example.anime.ui.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.anime.R
import com.example.anime.ui.navigation.NavItem
import com.example.anime.ui.utils.UiConstants.EMPTY_STRING
import com.example.anime.ui.utils.UiConstants.THREE_VALUE

@Composable
fun DetailScreen(
    viewModel: DetailScreenViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    DetailScreenContent(viewModel.detailUiState) { id ->
        navHostController.navigate(NavItem.Character.routeWithArgs(id))
    }
}

@Composable
fun DetailScreenContent(
    detailUiState: DetailUiState,
    navigateToCharacter: (Int) -> Unit
) {
    val detailItem by detailUiState.detailItem.collectAsState()
    detailItem?.let { anime ->
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(model = anime.coverImage, contentDescription = null)
            Text(text = stringResource(id = R.string.title, anime.title))
            Text(text = stringResource(id = R.string.episodes, anime.episodes))
            Text(text = stringResource(id = R.string.score, anime.score))
            Row {
                anime.genres.forEach { genre ->
                    Text(text = stringResource(id = R.string.genres, genre ?: EMPTY_STRING))
                }
            }
            Text(text = stringResource(id = R.string.english, anime.englishName ))
            Text(text = stringResource(id = R.string.japanese, anime.japaneseName))
            Text(text = stringResource(id = R.string.description, anime.description))
            LazyVerticalGrid(columns = GridCells.Fixed(THREE_VALUE)) {
                anime.characters.forEach { character ->
                    item {
                        Card(
                            modifier = Modifier.clickable { navigateToCharacter(character.id) }
                        ) {
                            AsyncImage(model = character.imageUrl, contentDescription = null)
                            Text(text = stringResource(id = R.string.character, character.name))
                        }
                    }
                }
            }
        }
    }
}
