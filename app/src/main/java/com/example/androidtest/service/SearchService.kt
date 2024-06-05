package com.example.androidtest.service

import com.example.androidtest.model.SearchApi
import com.example.androidtest.model.request.SearchRequest
import com.example.androidtest.model.response.SearchResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchService @Inject constructor(private val searchApi: SearchApi) {
    suspend fun search(query: String): SearchResponse {
        val searchRequest = SearchRequest(query)
        return searchApi.searchImages(searchRequest)
    }
}