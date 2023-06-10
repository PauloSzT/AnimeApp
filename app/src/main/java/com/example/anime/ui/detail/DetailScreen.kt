package com.example.anime.ui.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.anime.R
import com.example.anime.ui.models.UiDetailAnime
import com.example.anime.ui.navigation.NavItem
import com.example.anime.ui.theme.AnimeAppTheme
import com.example.anime.ui.utils.UiConstants.EMPTY_STRING
import com.example.anime.ui.utils.UiConstants.THREE_VALUE
import kotlinx.coroutines.flow.MutableStateFlow

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
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(3.0f),
                contentScale = ContentScale.FillBounds,
                model = anime.coverImage,
                contentDescription = anime.title
            )
            LazyColumn(
                modifier = Modifier.weight(3.5f)
            ) {
                item {
                    Text(
                        modifier = Modifier.weight(0.5f),
                        text = stringResource(id = R.string.title, anime.title)
                    )
                    Text(
                        modifier = Modifier.weight(0.5f),
                        text = stringResource(id = R.string.episodes, anime.episodes)
                    )
                    Text(
                        modifier = Modifier.weight(0.5f),
                        text = stringResource(id = R.string.score, anime.score)
                    )
                    Column(modifier = Modifier.weight(0.5f)) {
                        anime.genres.forEach { genre ->
                            Text(text = stringResource(id = R.string.genres, genre ?: EMPTY_STRING))
                        }
                    }
                    Text(
                        modifier = Modifier.weight(0.5f),
                        text = stringResource(id = R.string.english, anime.englishName)
                    )
                    Text(
                        modifier = Modifier.weight(0.5f),
                        text = stringResource(id = R.string.japanese, anime.japaneseName)
                    )
                    Text(
                        modifier = Modifier.weight(0.5f),
                        text = stringResource(id = R.string.description, anime.description)
                    )
                }
            }
            LazyVerticalGrid(
                modifier = Modifier.weight(1.5f),
                columns = GridCells.Fixed(THREE_VALUE)
            ) {
                anime.characters.forEach { character ->
                    item {
                        Card(
                            modifier = Modifier.clickable { navigateToCharacter(character.id) }
                        ) {
                            Column {
                                AsyncImage(
                                    modifier = Modifier.fillMaxSize(),
                                    model = character.imageUrl,
                                    contentScale = ContentScale.FillBounds,
                                    contentDescription = character.name
                                )
                                Text(
                                    text = character.name
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    val detailUiState = DetailUiState(
        detailItem = MutableStateFlow(
            UiDetailAnime(
                id = 1,
                coverImage = ("app/src/main/res/drawable/preview.jpg"),
                title = "DragonBall Z",
                episodes = 2,
                score = 100,
                genres = emptyList(),
                englishName = "DragonBall Z",
                japaneseName = "DragonBall Z",
                description = "Anime of DragonBall Z",
                characters = listOf()
                )
        )
    )
    AnimeAppTheme {
        DetailScreenContent(detailUiState = detailUiState, {})
    }
}
