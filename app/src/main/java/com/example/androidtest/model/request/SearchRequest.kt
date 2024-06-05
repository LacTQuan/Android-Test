package com.example.androidtest.model.request

data class SearchRequest(
    val q: String = "",
    val gl: String = "us",
    val type: String = "images",
    val engine: String = "google",
    val num: Int = 10
)