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
import com.juandgaines.challengeplaces.presentation.MasterDetailMapRoot
import com.juandgaines.challengeplaces.presentation.SearchLocationViewModel
import com.juandgaines.challengeplaces.ui.theme.ChallengePlacesTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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