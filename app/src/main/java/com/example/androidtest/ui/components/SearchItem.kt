package com.example.androidtest.ui.components

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
import coil.ImageLoader
import coil.compose.AsyncImage
import com.example.androidtest.model.response.Image

@Composable
fun SearchItem(
    item: Image,
    onClick: (Int) -> Unit
) {
    val width = 150.dp
    val height = width * (item.thumbnailHeight.toFloat() / item.thumbnailWidth.toFloat())
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                        .clip(RoundedCornerShape(10.dp))
                        .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(10.dp)),
                )
            }
        )
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
    SearchItem(
        item = Image(
            title = "A Strategic Analysis of Apple Inc.",
            imageUrl = "https://media.licdn.com/dms/image/C4D12AQFNv_KSo_VCwQ/article-cover_image-shrink_600_2000/0/1638142508773?e=2147483647&v=beta&t=SoxCwfG_3-FF8YnKRQNmBv0k0zOPe26PI6-1Nda-GrE",
            imageWidth = 740,
            imageHeight = 415,
            thumbnailUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTpOBvEgzRNu284eO7Mw-_IKukYnD2CXhGMTs1rjPcJj45uRiyr&s",
            thumbnailWidth = 300,
            thumbnailHeight = 168,
            source = "LinkedIn",
            domain = "www.linkedin.com",
            link = "https://www.linkedin.com/pulse/strategic-analysis-apple-inc-bidemi-ogedengbe",
            googleUrl = "https://www.google.com/imgres?imgurl=https%3A%2F%2Fmedia.licdn.com%2Fdms%2Fimage%2FC4D12AQFNv_KSo_VCwQ%2Farticle-cover_image-shrink_600_2000%2F0%2F1638142508773%3Fe%3D2147483647%26v%3Dbeta%26t%3DSoxCwfG_3-FF8YnKRQNmBv0k0zOPe26PI6-1Nda-GrE&tbnid=E8hnCY8LIxTZ3M&imgrefurl=https%3A%2F%2Fwww.linkedin.com%2Fpulse%2Fstrategic-analysis-apple-inc-bidemi-ogedengbe&docid=gP0JwewjX407kM&w=740&h=415&ved=0ahUKEwjgwaabicSGAxVrGFkFHUtOBE0QvFcIAigA",
            position = 1
        ),
        onClick = {}
    )
}
