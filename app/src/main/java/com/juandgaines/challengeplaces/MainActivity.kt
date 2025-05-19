package com.juandgaines.challengeplaces

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.juandgaines.challengeplaces.domain.city.RemoteCitiesDataSource
import com.juandgaines.challengeplaces.domain.city.CityTrie
import com.juandgaines.challengeplaces.presentation.MasterDetailMapRoot
import com.juandgaines.challengeplaces.presentation.SearchLocationViewModel
import com.juandgaines.challengeplaces.ui.theme.ChallengePlacesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var dataSource:RemoteCitiesDataSource

    val citiTrie = CityTrie()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch (Dispatchers.IO){

        }

        enableEdgeToEdge()
        setContent {
            ChallengePlacesTheme {
                val viewModel = hiltViewModel<SearchLocationViewModel>()
                MasterDetailMapRoot(
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChallengePlacesTheme {
        Greeting("Android")
    }
}