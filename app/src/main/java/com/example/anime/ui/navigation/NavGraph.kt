package com.example.anime.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.anime.ui.character.CharacterScreen
import com.example.anime.ui.detail.DetailScreen
import com.example.anime.ui.favorites.FavoritesScreen
import com.example.anime.ui.favorites.FavoritesScreenViewModel
import com.example.anime.ui.search.SearchScreen

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navHostController,
        startDestination = NavItem.Search.route,
        modifier = modifier.fillMaxSize()
    ) {
        composable(route = NavItem.Detail.route) {
            DetailScreen(navHostController = navHostController)
        }
        composable(route = NavItem.Favorites.route) {
            FavoritesScreen(
                viewModel = FavoritesScreenViewModel()
            )
        }
        composable(route = NavItem.Search.route) {
            SearchScreen(navHostController = navHostController)
        }
        composable(route = NavItem.Character.route) {
            CharacterScreen(navHostController = navHostController)
        }
    }
}
