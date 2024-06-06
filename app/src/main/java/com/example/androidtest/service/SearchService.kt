package com.example.androidtest.service

import android.util.Log
import com.example.androidtest.model.SearchApi
import com.example.androidtest.model.request.SearchRequest
import com.example.androidtest.model.response.AutoCompleteResponse
import com.example.androidtest.model.response.SearchResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchService @Inject constructor(private val searchApi: SearchApi) {
    suspend fun search(query: String, page: Int): SearchResponse {
        val searchRequest = SearchRequest(q = query, gl = "vn", page = page)
        return searchApi.searchImages(searchRequest)
    }

    suspend fun autocomplete(query: String): AutoCompleteResponse {
        val searchRequest = SearchRequest(q = query, gl = "vn", type = "autocomplete")
        return searchApi.autocomplete(searchRequest)
    }
}