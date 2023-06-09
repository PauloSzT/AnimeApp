package com.example.anime.ui.navigation

import com.example.anime.R
import com.example.anime.ui.navigation.NavConstants.CHARACTER_SCREEN_TITLE
import com.example.anime.ui.navigation.NavConstants.DETAIL_SCREEN_TITLE
import com.example.anime.ui.navigation.NavConstants.FAVORITES_SCREEN_ROUTE
import com.example.anime.ui.navigation.NavConstants.FAVORITES_SCREEN_TITLE
import com.example.anime.ui.navigation.NavConstants.SEARCH_SCREEN_ROUTE
import com.example.anime.ui.navigation.NavConstants.SEARCH_SCREEN_TITLE
import com.example.anime.ui.utils.UiConstants.EMPTY_STRING


sealed class NavItem(var title: String, var icon: Int, var route: String) {

    object Detail : NavItem(DETAIL_SCREEN_TITLE, R.drawable.ic_detail, "detail/{animeId}"){
        fun routeWithArgs(id: Int): String = "detail/${id}"
    }
    object Search : NavItem(SEARCH_SCREEN_TITLE, R.drawable.ic_search, SEARCH_SCREEN_ROUTE)
    object Favorites : NavItem(FAVORITES_SCREEN_TITLE, R.drawable.ic_favorites, FAVORITES_SCREEN_ROUTE)
    object Character : NavItem(CHARACTER_SCREEN_TITLE, R.drawable.ic_character, "character/{characterId}"){
        fun routeWithArgs(id: Int): String = "character/${id}"
    }

    companion object {
        fun String.title(): String {
            return when (this) {
                Detail.route -> Detail.title
                Search.route -> Search.title
                Favorites.route -> Favorites.title
                else -> EMPTY_STRING
            }
        }
    }
}
