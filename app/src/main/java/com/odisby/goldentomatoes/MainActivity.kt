package com.odisby.goldentomatoes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.odisby.goldentomatoes.core.ui.theme.GoldenTomatoesTheme
import com.odisby.goldentomatoes.feature.search.ui.SearchScreenRoot
import com.odisby.goldentomatoes.feature.search.ui.SearchViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GoldenTomatoesTheme {
                val viewModel by viewModels<SearchViewModel>()

                SearchScreenRoot(
                    navController = rememberNavController(),
                    viewModel = viewModel
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
    GoldenTomatoesTheme {
        Greeting("Android")
    }
}