package com.juandgaines.challengeplaces

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.juandgaines.challengeplaces.domain.city.RemoteCitiesDataSource
import com.juandgaines.challengeplaces.domain.city.CityTrie
import com.juandgaines.challengeplaces.ui.theme.ChallengePlacesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var dataSource:RemoteCitiesDataSource

    val citiTrie = CityTrie()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch (Dispatchers.IO){
            val cities = dataSource.getCities().getOrNull()
            cities?.forEach {
                citiTrie.insert(it)
            }
            cities
        }

        enableEdgeToEdge()
        setContent {
            ChallengePlacesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
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