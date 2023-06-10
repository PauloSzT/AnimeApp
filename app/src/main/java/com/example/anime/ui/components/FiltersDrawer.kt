package com.example.anime.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.anime.R
import com.example.anime.ui.search.SearchUiState

@Composable
fun FiltersDrawer(
    searchUiState: SearchUiState,
    closeDrawer: () -> Unit
) {
    val mediaSortFilters by searchUiState.mediaSortFilters.collectAsState()
    val typeFilters by searchUiState.typeFilters.collectAsState()
    val selectedTypeFilter by searchUiState.selectedTypeFilter.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.section_filters),
            modifier = Modifier.padding(vertical = 16.dp),
            style = MaterialTheme.typography.titleMedium
        )
        LazyVerticalGrid(columns = GridCells.Fixed(3)) {
            typeFilters.forEach { filter ->
                item {
                    FilterButton(
                        filterName = filter.name,
                        isSelected = selectedTypeFilter?.name == filter.name
                    ) {
                        searchUiState.onTypeFilterClick(filter)
                    }
                }
            }
        }
        Text(
            text = stringResource(id = R.string.type_filters),
            modifier = Modifier.padding(vertical = 16.dp),
            style = MaterialTheme.typography.titleMedium
        )
        LazyVerticalGrid(columns = GridCells.Fixed(3)) {
            mediaSortFilters.forEach { filter ->
                item {
                    FilterButton(
                        filterName = filter.filter.name,
                        isSelected = filter.isSelected
                    ) {
                        searchUiState.onSortFilterClick(filter)
                    }
                }
            }
        }
    }
    NavigationDrawerItem(
        label = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.close),
                textAlign = TextAlign.Center
            )
        },
        selected = false,
        onClick = { closeDrawer() },
        modifier = Modifier.width(100.dp),
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = MaterialTheme.colorScheme.onBackground,
            unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
            unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
            unselectedBadgeColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}
