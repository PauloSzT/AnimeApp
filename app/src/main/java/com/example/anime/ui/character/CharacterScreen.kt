package com.example.anime.ui.character

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.anime.R

@Composable
fun CharacterScreen(
    viewModel: CharacterScreenViewModel = hiltViewModel(),
) {
    CharacterScreenContent(viewModel.characterUiState)
}

@Composable
fun CharacterScreenContent(
    characterUiState: CharacterUiState
) {
    val characterItem by characterUiState.characterItem.collectAsState()
    characterItem?.let { character ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                AsyncImage(
                    model = character.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(250.dp)
                        .width(250.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                )
                Column{
                    Text(text = stringResource(id = R.string.full_name, character.name))
                    Text(text = stringResource(id = R.string.description, character.description))
                    Text(text = stringResource(id = R.string.gender, character.gender))
                    Text(text = stringResource(id = R.string.date_of_birth, character.dateOfBirth))
                    Text(text = stringResource(id = R.string.age, character.age))
                    Text(text = stringResource(id = R.string.blood_type, character.bloodType))
                }
            }
        }
    }
}
