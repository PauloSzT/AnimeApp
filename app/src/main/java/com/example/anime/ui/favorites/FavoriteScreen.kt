package com.example.anime.ui.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController

@Composable
fun FavoritesScreen(
    navHostController: NavHostController
){
    FavoritesScreenContent()
}

@Composable
fun FavoritesScreenContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ){
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Red
            )
        ) {}
    }
}
