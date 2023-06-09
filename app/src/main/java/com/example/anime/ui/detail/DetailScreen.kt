package com.example.anime.ui.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.anime.ui.navigation.NavItem

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
            Text(text = anime.title)
            Text(text = anime.episodes.toString())
            Text(text = anime.score.toString())
            Row {
                anime.genres.forEach { genre ->
                    Text(text = genre ?: "")
                }
            }
            Text(text = anime.englishName)
            Text(text = anime.japaneseName)
            Text(text = anime.description)
            LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                anime.characters.forEach { character ->
                    item {
                        Card(
                            modifier = Modifier.clickable { navigateToCharacter(character.id) }
                        ) {
                            AsyncImage(model = character.imageUrl, contentDescription = null)
                            Text(text = character.name)
                        }
                    }
                }
            }
        }
    }
}
