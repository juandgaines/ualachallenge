package com.juandgaines.challengeplaces.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.juandgaines.challengeplaces.R

@Composable
fun ListPlaces(
    state: SearchState,
    query:String,
    onAction:(CitiesIntent) -> Unit,
    navigateToDetail:()->Unit
) {
    val loadingDescription = stringResource(R.string.loading_description)
    val searchScreenDescription = stringResource(R.string.search_screen_description)
    val searchCheckFavoritesDescription = stringResource(R.string.search_screen_check_description)
    val toggleFavorite = stringResource(R.string.search_screen_item_toggle_favorite_description)
    val listState = rememberLazyListState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {

            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(
                            Alignment.Center
                        )
                        .semantics {
                            contentDescription = loadingDescription
                        }
                )
            }

            else -> {
                Column(
                    Modifier
                        .fillMaxSize()
                        .semantics {
                            contentDescription = searchScreenDescription
                        },
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column {
                        SearchTextField(
                            text = query,
                            onTextChange = { newText ->
                                onAction(CitiesIntent.OnQueryChange(newText))
                            },
                            onClearClick = {
                                onAction(CitiesIntent.OnClearClick)
                            },
                        )
                        Row {
                            Checkbox(
                                checked = state.isFavoriteFilter,
                                onCheckedChange = {
                                    onAction(
                                        CitiesIntent.OnShowFavorites(it)
                                    )
                                },
                                modifier = Modifier
                                    .semantics {
                                        contentDescription = searchCheckFavoritesDescription
                                    }
                            )
                            Text(
                                text = stringResource(R.string.show_favorites),
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 8.dp)
                            )
                        }
                    }

                    if(state.suggestions.isEmpty()){
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {

                            Text(
                                text = if (query.isEmpty())
                                    stringResource(R.string.start)
                                else
                                    stringResource(R.string.location_not_found,query),
                                modifier = Modifier.align(
                                    Alignment.Center
                                )
                            )
                        }
                    }
                    else{
                        LazyColumn (
                            state = listState,
                            contentPadding = PaddingValues(vertical = 8.dp)
                        ){
                            items(
                                state.suggestions,
                                key = {it.id}
                            ) { item ->
                                Row (
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            onAction(
                                                CitiesIntent.OnCityClick(item)
                                            )
                                            navigateToDetail()
                                        }
                                        .then(
                                            if (state.currentSelectedCity?.id == item.id) {
                                                Modifier.border(
                                                    width = 2.dp,
                                                    color = MaterialTheme.colorScheme.primary,
                                                    shape = RoundedCornerShape(8.dp)
                                                )
                                            } else {
                                                Modifier
                                            }
                                        )
                                        .padding(16.dp)
                                ){
                                    Column(
                                        modifier = Modifier.weight(1f),
                                    ) {
                                        Text(
                                            text = item.name + ", " + item.country,
                                            modifier = Modifier.fillMaxWidth(),
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                        Text(
                                            text = "Lat: ${item.lat}, Lon: ${item.lon}",
                                        )
                                        HorizontalDivider(
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }

                                    Image(
                                        imageVector = if (
                                            item.isFavorite
                                        ) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                        contentDescription = "Arrow",
                                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .semantics {
                                                contentDescription = toggleFavorite
                                            }
                                            .clickable {
                                                onAction(
                                                    CitiesIntent.ToggleFavorite(item)
                                                )
                                            }
                                            .align(Alignment.CenterVertically)
                                    )
                                }
                            }
                        }
                    }


                }
            }
        }
    }
}