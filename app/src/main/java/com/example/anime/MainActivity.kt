package com.example.anime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.anime.ui.navigation.NavItem
import com.example.anime.ui.navigation.NavItem.Companion.title
import com.example.anime.ui.navigation.NavigationGraph
import com.example.anime.ui.theme.AnimeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            var title by remember { mutableStateOf("") }
            var displayTopBar by remember { mutableStateOf(true) }
            DisposableEffect(key1 = Unit) {
                val destinationListener =
                    NavController.OnDestinationChangedListener { _, destination, _ ->
                        displayTopBar = destination.route != NavItem.Search.route
                        title = destination.route?.title().orEmpty()
                    }
                navHostController.addOnDestinationChangedListener(destinationListener)
                onDispose { navHostController.removeOnDestinationChangedListener(destinationListener) }
            }
            AnimeAppTheme {
                Scaffold(
                    topBar = {
                        Column(
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = title,
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                },
                                navigationIcon = {
                                    if (displayTopBar) {
                                        IconButton(onClick = { navHostController.navigateUp() }) {
                                            Icon(Icons.Filled.ArrowBack, null)
                                        }
                                    }
                                },
                                actions = {
                                    IconButton(onClick = { navHostController.navigate(NavItem.Favorites.route)}) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_favorites),
                                            contentDescription = null
                                        )
                                    }
                                },
                                modifier = Modifier
                                    .background(color = Color.White)
                            )
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .padding(horizontal = 16.dp),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    NavigationGraph(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        navHostController = navHostController
                    )
                }
            }
        }
    }
}
