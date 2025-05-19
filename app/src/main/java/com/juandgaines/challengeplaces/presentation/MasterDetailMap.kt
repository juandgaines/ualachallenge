package com.juandgaines.challengeplaces.presentation

import android.view.SearchEvent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.util.query
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
    ){ paddingValues->

        NavigableListDetailPaneScaffold(
            modifier = Modifier.padding(paddingValues),
            navigator = scaffoldNavigator,
            listPane = {
                AnimatedPane {
                    Box (
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        when{
                            state.isLoading->{
                                CircularProgressIndicator(
                                    modifier = Modifier.align(
                                        Alignment.Center
                                    )
                                )
                            }
                            else->{
                                Column (Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues)){
                                    SearchTextField(
                                        text = query,
                                        onTextChange = { newText ->
                                            viewModel.onAction(CitiesIntent.OnQueryChange(newText))
                                        },
                                        onClearClick = {
                                            viewModel.onAction(CitiesIntent.OnClearClick)
                                        },
                                    )
                                    LazyColumn {
                                        items(state.suggestions) { item ->
                                            Column(
                                                modifier = Modifier.clickable {
                                                    //Todo: Select item
                                                    scope.launch {
                                                        scaffoldNavigator.navigateTo(
                                                            ListDetailPaneScaffoldRole.Detail
                                                        )
                                                    }
                                                }
                                            ) {
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