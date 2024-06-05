package com.example.androidtest.model.response

data class SearchParameters(
    val q: String,
    val gl: String,
    val type: String,
    val engine: String,
    val num: Int
)

data class Image(
    val title: String,
    val imageUrl: String,
    val imageWidth: Int,
    val imageHeight: Int,
    val thumbnailUrl: String,
    val thumbnailWidth: Int,
    val thumbnailHeight: Int,
    val source: String,
    val domain: String,
    val link: String,
    val googleUrl: String,
    val position: Int
)

data class SearchResponse(
    val searchParameters: SearchParameters,
    val images: List<Image>
)
