package com.juandgaines.challengeplaces.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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

    val state by viewModel.state.collectAsStateWithLifecycle()

    BackHandler(enabled = scaffoldNavigator.canNavigateBack()) {
        scope.launch {
            scaffoldNavigator.navigateBack()
        }
    }

    Scaffold { _->
        NavigableListDetailPaneScaffold(
            modifier = Modifier.safeContentPadding(),
            navigator = scaffoldNavigator,
            listPane = {
                AnimatedPane {
                    LazyColumn {
                        items(state.suggestions){ item->
                            Column (
                                modifier = Modifier.clickable {
                                    //Todo: Select item
                                    scope.launch {
                                        scaffoldNavigator.navigateTo(
                                            ListDetailPaneScaffoldRole.Detail
                                        )
                                    }
                                }
                            ){
                                Text(
                                    text = item.country
                                )
                                Text(
                                    text = item.name
                                )
                            }
                        }
                    }
                }
            },
            detailPane = {
                AnimatedPane {
                    val isDetailVisible =
                        scaffoldNavigator.scaffoldValue[ListDetailPaneScaffoldRole.Detail] == PaneAdaptedValue.Expanded
                    Text(
                        "Detail Item",
                        modifier = Modifier
                    )
                }
            }
        )
    }

}