package com.juandgaines.challengeplaces.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
                    ListPlaces(
                        query = query,
                        state = state,
                        onAction= viewModel::onAction
                    ){
                        scope.launch {
                            scaffoldNavigator.navigateTo(
                                ListDetailPaneScaffoldRole.Detail
                            )
                        }
                    }
                }
            },
            detailPane = {
                AnimatedPane {

                    DetailPlaces(
                        state = state,
                        isDetailVisible =
                            scaffoldNavigator.scaffoldValue[ListDetailPaneScaffoldRole.Detail] == PaneAdaptedValue.Expanded && scaffoldNavigator.scaffoldValue[ListDetailPaneScaffoldRole.List] == PaneAdaptedValue.Expanded
                        ,
                        onBackPressed = {
                            viewModel.onAction(CitiesIntent.OnCityClick(null))
                            scope.launch {
                                scaffoldNavigator.navigateBack()
                            }
                        },
                    )
                }
            }
        )
    }
}

