package com.example.androidtest.ui.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.androidtest.model.response.Image

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SearchItem(
    item: Image,
    onClick: (Int) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val width = 150.dp
    val height = width * (item.thumbnailHeight.toFloat() / item.thumbnailWidth.toFloat())
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        with(sharedTransitionScope) {
            Box(
                modifier = Modifier
                    .width(width)
                    .height(height)
                    .fillMaxWidth()
                    .clickable(onClick = {
                        onClick(item.position)
                    }),
                content = {
                    AsyncImage(
                        model = item.thumbnailUrl,
                        contentDescription = item.title,
                        imageLoader = ImageLoader(context = LocalContext.current),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(0.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .sharedElement(
                                rememberSharedContentState(key = item.position),
                                animatedVisibilityScope = animatedVisibilityScope,
                            )
                            .zIndex(0f)
                    )
                }
            )
        }
        Text(
            text = item.title,
            maxLines = 2,
            modifier = Modifier.padding(4.dp),
            color = Color.Black,
            fontSize = 14.sp
        )
    }
}

@Preview
@Composable
fun SearchItemPreview() {

}
