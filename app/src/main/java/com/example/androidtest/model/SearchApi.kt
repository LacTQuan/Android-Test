package com.example.androidtest.model

import android.util.Log
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
        Log.d("SearchApi", "Autocomplete request: $request")
        val response = service.autocomplete(request)
        Log.d("SearchApi", "Autocomplete response: ${response.suggestions}")
        return response
    }

    interface Service {
        @POST("images")
        suspend fun searchImages(@Body request: SearchRequest): SearchResponse

        @POST("autocomplete")
        suspend fun autocomplete(@Body request: SearchRequest): AutoCompleteResponse
    }
}