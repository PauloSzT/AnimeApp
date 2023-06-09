package com.example.anime.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.anime.R
import com.example.anime.ui.models.UiAnimeListItem

@Composable
fun SearchItemCard(
    uiAnimeListItem: UiAnimeListItem,
    favoritesIdsState: List<Int>,
    onFavoriteClick: (UiAnimeListItem) -> Unit,
    navigateToDetails: (Int) -> Unit
) {
    Surface(
        modifier = Modifier.padding(4.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clickable { navigateToDetails(uiAnimeListItem.id) },
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2.0f),
                    contentScale = ContentScale.Crop,
                    model = uiAnimeListItem.coverImage,
                    contentDescription = uiAnimeListItem.title
                )
                Row(
                    modifier = Modifier
                        .weight(2.0f)
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = uiAnimeListItem.title,
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 2,
                        minLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1.5f),
                        softWrap = true
                    )
                    Icon(
                        modifier = Modifier
                            .padding(top = 8.dp, end = 8.dp)
                            .weight(0.5f)
                            .clickable { onFavoriteClick(uiAnimeListItem) },
                        painter = painterResource(
                            if (favoritesIdsState.contains(uiAnimeListItem.id)) R.drawable.ic_start_fill else R.drawable.ic_start_empty
                        ),
                        contentDescription = null
                    )
                }
            }
        }
    }
}
