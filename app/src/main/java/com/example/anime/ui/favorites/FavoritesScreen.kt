package com.example.anime.ui.favorites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.anime.R
import com.example.anime.ui.models.UiFavoriteAnime
import com.example.anime.ui.theme.AnimeAppTheme
import com.example.anime.ui.theme.NotoSerif
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.abs

@Composable
fun FavoritesScreen(
    viewModel: FavoritesScreenViewModel = hiltViewModel()
) {
    FavoritesScreenContent(viewModel.favoritesUiState)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoritesScreenContent(
    favoritesUiState: FavoritesUiState
) {
    val favoriteList by favoritesUiState.favoriteList.collectAsState()
    val favoriteListState = rememberPagerState { favoriteList.size }
    val windowWidth = LocalConfiguration.current.screenWidthDp.dp
    val pageSize = windowWidth.times(0.5f)
    val padding = windowWidth.times(0.2f)
    val orientation = LocalConfiguration.current.orientation
    val isHorizontal = orientation != Orientation.Horizontal.ordinal

    if (favoriteList.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                state = favoriteListState,
                pageSize =
                PageSize.Fixed(
                    pageSize + padding
                ),
                contentPadding = PaddingValues(
                    horizontal = (windowWidth - pageSize).div(
                        2
                    )
                )
            ) { pageNumber ->
                FavoriteItemComponent(
                    modifier = Modifier
                        .width(pageSize)
                        .height(if (isHorizontal) pageSize.div(2) else pageSize.times(1.5f))
                        .graphicsLayer {
                            val currentOffset =
                                abs(favoriteListState.calculateCurrentOffsetForPage(pageNumber))
                            scaleX = 1.5f - 0.5
                                .times(currentOffset)
                                .toFloat()
                            scaleY = 1.5f - 0.5
                                .times(currentOffset)
                                .toFloat()
                        },
                    isHorizontal = isHorizontal,
                    uiFavoriteAnime = favoriteList[pageNumber],
                    onDeleteFavoriteAnimeClick = favoritesUiState.onDeleteFavoriteAnimeClick
                )
            }
        }

    } else {
        NoFavoritesSaved()
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}

@Composable
fun FavoriteItemComponent(
    uiFavoriteAnime: UiFavoriteAnime,
    modifier: Modifier,
    isHorizontal: Boolean,
    onDeleteFavoriteAnimeClick: (UiFavoriteAnime) -> Unit
) {

    Card(
        modifier = modifier
            .fillMaxHeight()
    ) {
        if (isHorizontal) {
            Row(
                modifier = Modifier.padding(10.dp)
            ) {
                if (LocalInspectionMode.current) {
                    Image(
                        painter = painterResource(id = R.drawable.preview),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight
                    )
                } else {
                    AsyncImage(
                        modifier = Modifier.weight(1.0f),
                        contentScale = ContentScale.FillBounds,
                        model = uiFavoriteAnime.coverImage,
                        contentDescription = null
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1.0f)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp, top = 16.dp),
                        textAlign = TextAlign.Center,
                        text = uiFavoriteAnime.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Button(
                        modifier = Modifier.padding(bottom = 16.dp),
                        onClick = { onDeleteFavoriteAnimeClick(uiFavoriteAnime) }) {
                        Text(
                            text = stringResource(id = R.string.delete),
                            fontSize = 10.sp
                        )
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                AsyncImage(
                    modifier = Modifier.weight(3.0f),
                    contentScale = ContentScale.FillBounds,
                    model = uiFavoriteAnime.coverImage,
                    contentDescription = null
                )
                Text(
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .weight(1.3f),
                    text = uiFavoriteAnime.title,
                    fontFamily = NotoSerif,
                    style = MaterialTheme.typography.titleMedium
                )
                Button(
                    modifier = Modifier.weight(0.7f),
                    onClick = { onDeleteFavoriteAnimeClick(uiFavoriteAnime) }) {
                    Text(
                        text = stringResource(id = R.string.delete),
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}


@Composable
fun NoFavoritesSaved() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.no_favorites_saved))
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    val favoriteUiState = FavoritesUiState(
        favoriteList = MutableStateFlow(
            listOf(
                UiFavoriteAnime(
                    id = 1,
                    title = "Goku",
                    coverImage = "https://staticg.sportskeeda.com/editor/2022/01/410ce-16424556600474-1920.jpg",
                )
            )
        ),
        onDeleteFavoriteAnimeClick = {}
    )
    AnimeAppTheme {
        FavoritesScreenContent(favoritesUiState = favoriteUiState)
    }
}
