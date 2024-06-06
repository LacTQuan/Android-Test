package com.example.androidtest

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidtest.ui.features.details_screen.DetailsScreen
import com.example.androidtest.ui.features.search_screen.SearchContract
import com.example.androidtest.ui.features.search_screen.SearchScreen
import com.example.androidtest.ui.features.search_screen.SearchViewModel
import com.example.androidtest.ui.theme.AndroidTestTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.emptyFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SearchingApp()
                }
            }
        }
    }
}

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun SearchingApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "searchScreen") {
        composable("searchScreen") {
            val viewModel: SearchViewModel = hiltViewModel(navController.getBackStackEntry("searchScreen"))
            SearchScreen(
                viewModel = viewModel,
                onNavigationRequested = { position ->
                    navController.navigate("detailsScreen/$position")
                }
            )
        }

        composable("detailsScreen/{position}") { backStackEntry ->
            val viewModel: SearchViewModel = hiltViewModel(navController.getBackStackEntry("searchScreen"))
            val position = backStackEntry.arguments?.getString("position")
            DetailsScreen(viewModel = viewModel, position = position?.toInt() ?: 0, onBack = {
                Log.d("SearchingApp", "onBack")
                navController.popBackStack()
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidTestTheme {

    }
}