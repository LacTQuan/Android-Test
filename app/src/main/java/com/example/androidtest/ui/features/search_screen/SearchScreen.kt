package com.example.androidtest.ui.features.search_screen

//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemInfo
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.androidtest.R
import com.example.androidtest.ui.components.DropdownMenu
import com.example.androidtest.ui.components.LoadingAnimation
import com.example.androidtest.ui.components.MySearchBar
import com.example.androidtest.ui.components.SearchItem

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
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
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .background(Color.Transparent),
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
                title = {
                    MySearchBar(
                        query = viewModel.state.query,
                        onQueryChange = viewModel::onQueryChange,
                        onActiveChange = viewModel::onActiveChange,
                        onSearch = viewModel::onSearch,
                        active = viewModel.state.isActive
                    )
                },
                scrollBehavior = scrollBehavior
            )
//            Box(modifier = Modifier.fillMaxWidth()) {
//                MySearchBar(
//                    query = viewModel.state.query,
//                    onQueryChange = viewModel::onQueryChange,
//                    onActiveChange = viewModel::onActiveChange,
//                    onSearch = viewModel::onSearch,
//                    active = viewModel.state.isActive,
//                    suggestions = viewModel.state.suggestions,
////                    modifier = Modifier
////                        .fillMaxWidth()
////                        .background(Color(100, 255, 207, 128))
////                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 4.dp)
////                        .zIndex(2f)
////                        .align(Alignment.TopCenter)
//                )
//            }
        }
    ) {
        if (viewModel.state.isActive) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(1f)
            ) {
                DropdownMenu(
                    active = viewModel.state.isActive,
                    suggestions = viewModel.state.suggestions,
                    onQueryChange = viewModel::onQueryChange,
                    onSearch = viewModel::onSearch,
                    onActiveChange = viewModel::onActiveChange,
                    query = viewModel.state.query
                )
            }
        }
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
                try {
                    items(items.images.size) { index ->
                        SearchItem(
                            item = items.images[index],
                            onClick = {
                                onNavigationRequested(index)
                            },
                            sharedTransitionScope = sharedTransitionScope,
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    }
                } catch (e: Exception) {
                    throw e
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
