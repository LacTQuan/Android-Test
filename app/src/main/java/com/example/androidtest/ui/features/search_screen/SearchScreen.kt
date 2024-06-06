package com.example.androidtest.ui.features.search_screen

//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.androidtest.ui.components.MySearchBar
import com.example.androidtest.ui.components.SearchItem

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onNavigationRequested: (position: Int) -> Unit,
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val items = viewModel.state.searchResults
    val gridState = rememberLazyStaggeredGridState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MySearchBar(
                    query = viewModel.state.query,
                    onQueryChange = viewModel::onQueryChange,
                    onActiveChange = viewModel::onActiveChange,
                    onSearch = viewModel::onSearch,
                    active = viewModel.state.isActive,
                    suggestions = viewModel.state.suggestions
            )
        }
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(150.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp,
            state = gridState
        ) {
            if (items != null) {
                items(items.images.size) { index ->
                    SearchItem(item = items.images[index], onClick = {
                        onNavigationRequested(index)
                    })
                }

                // If user scrolls to the end of the list, load more items
                if (!gridState.canScrollForward) {
                    viewModel.loadMore()
                }
            }

        }
    }
}

data class ListItem(
    val height: Dp,
    val color: Color
)

@Preview
@Composable
fun SearchScreenPreview() {
//    SearchScreen(
//        onNavigationRequested = {}
//    )
}
