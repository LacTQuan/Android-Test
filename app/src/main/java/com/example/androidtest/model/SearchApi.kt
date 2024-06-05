package com.example.androidtest.model

import com.example.androidtest.model.request.SearchRequest
import com.example.androidtest.model.response.AutoCompleteResponse
import com.example.androidtest.model.response.SearchResponse
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchApi @Inject constructor(private val service: Service) {
    suspend fun searchImages(request: SearchRequest): SearchResponse {
        return service.searchImages(request)
    }

    suspend fun autocomplete(request: SearchRequest): AutoCompleteResponse {
        return service.autocomplete(request)
    }

    interface Service {
        @POST("images")
        suspend fun searchImages(@Body request: SearchRequest): SearchResponse

        @POST("autocomplete")
        suspend fun autocomplete(@Body request: SearchRequest): AutoCompleteResponse
    }
}