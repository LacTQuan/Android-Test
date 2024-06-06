package com.example.androidtest.ui.features.details_screen

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.ImageLoader
import coil.compose.AsyncImage
import com.example.androidtest.ui.features.search_screen.SearchViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun DetailsScreen(
    viewModel: SearchViewModel,
    position: Int = 0,
    onBack: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope
) {
    val images = viewModel.state.searchResults?.images ?: emptyList()
    val pagerState = rememberPagerState(pageCount = { images.size }, initialPage = position)
    val openUrlLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {}

    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 8.dp)
                .align(Alignment.TopStart)
                .zIndex(1f)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) { index ->
            val width = 500.dp
            val height =
                width * (images[index].thumbnailHeight.toFloat() / images[index].thumbnailWidth.toFloat())

            with(sharedTransitionScope) {
                Box(
                    modifier = Modifier
                        .width(width)
                        .height(height)
                        .fillMaxWidth()
                ) {
                    AsyncImage(
                        model = images[index].thumbnailUrl,
                        contentDescription = null,
                        imageLoader = ImageLoader(LocalContext.current),
                        modifier = Modifier
                            .fillMaxSize()
                            .sharedElement(
                                rememberSharedContentState(key = images[index].position),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                    )
                }
            }


            if (index == images.size - 2) {
                viewModel.loadMore()
            }
        }

        Button(
            onClick = {
                // Handle button click to open website
                val url = images[pagerState.currentPage].link
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                openUrlLauncher.launch(intent)
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .widthIn(min = 200.dp)
                .zIndex(1f),
            colors = ButtonDefaults.buttonColors(Color.LightGray)
        ) {
            Text(text = "Go to website")
        }
    }
}