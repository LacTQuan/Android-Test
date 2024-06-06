package com.example.androidtest.ui.features.search_screen

//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.androidtest.R
import com.example.androidtest.ui.components.LoadingAnimation
import com.example.androidtest.ui.components.MySearchBar
import com.example.androidtest.ui.components.SearchItem

@OptIn(ExperimentalSharedTransitionApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onNavigationRequested: (position: Int) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope
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
        if (viewModel.state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0, 0, 0, 100))
                    .zIndex(1f)
            ) {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    LoadingAnimation()
                }
            }
        }
        if (items == null || items.images.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.empty_search_100),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 64.dp)
                )
                Text(
                    text = "No results found",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 32.dp)
                )
            }
        } else {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(150.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalItemSpacing = 16.dp,
                state = gridState
            ) {
                items(items.images.size) { index ->
                    SearchItem(item = items.images[index], onClick = {
                        onNavigationRequested(index)
                    }, sharedTransitionScope = sharedTransitionScope, animatedVisibilityScope = animatedVisibilityScope)
                }

                // If user scrolls to the end of the list, load more items
                if (!gridState.canScrollForward) {
                    viewModel.loadMore()
                }

            }
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
//    SearchScreen(
//        onNavigationRequested = {}
//    )
}
