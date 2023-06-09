package com.example.anime.ui.character

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.anime.ui.models.UiAnimeCharacterDetail

@Composable
fun CharacterScreen (
    viewModel: CharacterScreenViewModel = hiltViewModel(),
){
    CharacterScreenContent(viewModel.characterUiState)
}

@Composable
fun CharacterScreenContent(
    characterUiState: CharacterUiState
) {
    val characterItem by characterUiState.characterItem.collectAsState()
    characterItem?.let{character ->
        Column(
            modifier = Modifier.fillMaxSize()
        ){
            AsyncImage(
                model = character.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .height(250.dp)
                    .width(250.dp)
            )
            Text(text = "Full Name: ${character.name}")
            Text(text = "Description: ${character.description}")
            Text(text = "Gender: ${character.gender}")
            Text(text = "Date Of Birth: ${character.dateOfBirth}")
            Text(text = "Age: ${character.age}")
            Text(text = "Blood Type: ${character.bloodType}")
        }
    }
}
