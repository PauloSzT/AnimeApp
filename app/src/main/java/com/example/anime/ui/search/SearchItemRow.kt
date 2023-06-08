package com.example.anime.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.anime.R
import com.example.anime.ui.models.UiSearchResultAnime

@Composable
fun SearchItemRow(
    uiSearchResultAnime: UiSearchResultAnime,
    favoritesIdsState: List<Int>,
    onFavoriteClick: (UiSearchResultAnime) -> Unit,
    navigateToDetails: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row() {
            AsyncImage(
                modifier = Modifier.weight(1f),
                model = uiSearchResultAnime.coverImage,
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(2.5f),

                ) {
                Text(
                    text = uiSearchResultAnime.title,
                    modifier = Modifier
                        .padding(bottom = 8.dp),
                    softWrap = true
                )
            }
            Icon(
                modifier = Modifier
                    .padding(top = 16.dp, end = 8.dp)
                    .weight(0.5f)
                    .clickable { onFavoriteClick(uiSearchResultAnime) },
                painter = painterResource(
                    if (favoritesIdsState.contains(uiSearchResultAnime.id)) R.drawable.ic_start_fill else R.drawable.ic_start_empty
                ),
                contentDescription = null
            )
        }
    }
}