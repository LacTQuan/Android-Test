package com.example.androidtest.model

import com.example.androidtest.model.request.SearchRequest
import com.example.androidtest.model.response.AutoCompleteResponse
import com.example.androidtest.model.response.SearchResponse
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchApi @Inject constructor(private val service: SearchService) {
    suspend fun searchImages(request: SearchRequest): SearchResponse {
        return service.searchImages(request)
    }

    suspend fun autocomplete(request: SearchRequest): AutoCompleteResponse {
        return service.autocomplete(request)
    }

    interface SearchService {
        @POST("images")
        suspend fun searchImages(@Body request: SearchRequest): SearchResponse

        @POST("autocomplete")
        suspend fun autocomplete(@Body request: SearchRequest): AutoCompleteResponse
    }

    companion object {
        const val API_URL = "https://google.serper.dev/"
    }
}