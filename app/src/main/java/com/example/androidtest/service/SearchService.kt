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
        try {
            val searchRequest = SearchRequest(q = query, gl = "vn", page = page)
            return searchApi.searchImages(searchRequest)
        } catch (e: Exception) {
            Log.e("SearchService", "search: $e")
            throw e
        }
    }

    suspend fun autocomplete(query: String): AutoCompleteResponse {
        try {
            val searchRequest = SearchRequest(q = query, gl = "vn", type = "autocomplete")
            return searchApi.autocomplete(searchRequest)
        } catch (e: Exception) {
            Log.e("SearchService", "autocomplete: $e")
            throw e
        }
    }
}