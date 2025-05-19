package com.juandgaines.challengeplaces.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import kotlinx.coroutines.launch

@ExperimentalMaterial3AdaptiveApi
@Composable
fun MasterDetailMapRoot(viewModel: SearchLocationViewModel) {
    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<Any>()
    val scope = rememberCoroutineScope()

    BackHandler(enabled = scaffoldNavigator.canNavigateBack()) {
        scope.launch {
            scaffoldNavigator.navigateBack()
        }
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val query by viewModel.query.collectAsStateWithLifecycle()
    Scaffold (
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        NavigableListDetailPaneScaffold(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding(),
            navigator = scaffoldNavigator,
            listPane = {
                AnimatedPane {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        when {
                            state.isLoading -> {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(
                                        Alignment.Center
                                    )
                                )
                            }

                            else -> {
                                Column(
                                    Modifier
                                        .fillMaxSize(),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        SearchTextField(
                                            text = query,
                                            onTextChange = { newText ->
                                                viewModel.onAction(CitiesIntent.OnQueryChange(newText))
                                            },
                                            onClearClick = {
                                                viewModel.onAction(CitiesIntent.OnClearClick)
                                            },
                                        )
                                        Row {
                                            Checkbox(
                                                checked = state.isFavoriteFilter,
                                                onCheckedChange = {
                                                    viewModel.onAction(
                                                        CitiesIntent.OnShowFavorites(it)
                                                    )
                                                },
                                                modifier = Modifier.padding(16.dp)
                                            )
                                            Text(
                                                text = "Show only favorites",
                                                style = MaterialTheme.typography.titleMedium,
                                                modifier = Modifier
                                                    .align(Alignment.CenterVertically)
                                                    .padding(start = 8.dp)
                                            )
                                        }
                                    }

                                    LazyColumn (
                                        contentPadding = PaddingValues(16.dp)
                                    ){
                                        items(
                                            state.suggestions,
                                            key = {it.id}
                                        ) { item ->
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clickable {
                                                        viewModel.onAction(
                                                            CitiesIntent.OnCityClick(item)
                                                        )
                                                        scope.launch {
                                                            scaffoldNavigator.navigateTo(
                                                                ListDetailPaneScaffoldRole.Detail
                                                            )
                                                        }
                                                    }
                                            ) {
                                                Text(
                                                    text = item.name + ", "+item.country,
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
                                        }
                                    }

                                }
                            }
                        }
                    }

                }
            },
            detailPane = {
                AnimatedPane {
                    val isDetailVisible =
                        scaffoldNavigator.scaffoldValue[ListDetailPaneScaffoldRole.Detail] == PaneAdaptedValue.Expanded
                    Scaffold { padding ->
                        val cameraPositionState = rememberCameraPositionState()

                        val markerPosition = remember(state.currentSelectedCity) {
                            LatLng(
                                (state.currentSelectedCity?.lat?.toFloat() ?: 0f).toDouble(),
                                (state.currentSelectedCity?.lon?.toFloat() ?: 0f).toDouble()
                            )
                        }
                        val marker = rememberUpdatedMarkerState(markerPosition)

                        LaunchedEffect(state.currentSelectedCity) {
                            if (state.currentSelectedCity != null) {
                                val latLng = LatLng(
                                    state.currentSelectedCity?.lat ?: 0.0,
                                    state.currentSelectedCity?.lon ?: 0.0
                                )
                                cameraPositionState.animate(
                                    CameraUpdateFactory.newLatLngZoom(latLng, 17f)
                                )
                            }
                        }

                        GoogleMap(
                            cameraPositionState = cameraPositionState,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding)
                        ) {
                            Marker(
                                state = marker,
                            )
                        }
                    }

                }
            }
        )
    }
}

