package com.example.androidtest.ui.features.search_screen

//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.androidtest.ui.components.MySearchBar
import kotlin.random.Random

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    onNavigationRequested: (position: Int) -> Unit,
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    val items = (1..100).map {
        ListItem(
            height = Random.nextInt(100, 300).dp,
            color = Color(
                Random.nextLong(0xFFFFFFFF)
            ).copy(alpha = 1f)
        )
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MySearchBar(
//                    query = viewModel.state.query,
//                    onQueryChange = viewModel::onQueryChange,
//                    onActiveChange = viewModel::onActiveChange,
//                    onSearch = viewModel::onQueryChange,
//                    active = viewModel.state.isActive,
                query = "",
                onQueryChange = {},
                onActiveChange = {},
                onSearch = {},
                active = false,
            )
        }
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(150.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp,
        ) {
            items(items.size) { index ->
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(items[index].height)
                    .clip(RoundedCornerShape(10.dp))
                    .background(items[index].color))
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
    SearchScreen(
        onNavigationRequested = {}
    )
}
