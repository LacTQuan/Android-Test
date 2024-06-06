package com.example.androidtest.service

import android.util.Log
import com.example.androidtest.model.SearchApi
import com.example.androidtest.model.request.SearchRequest
import com.example.androidtest.model.response.AutoCompleteResponse
import com.example.androidtest.model.response.SearchResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import redis.clients.jedis.Jedis
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.withContext
import redis.clients.jedis.Connection

@Singleton
class SearchService @Inject constructor(
    private val searchApi: SearchApi,
    private val jedis: Jedis,
    private val gson: Gson
) {
    suspend fun search(query: String, page: Int): SearchResponse {
        try {

            return withContext(Dispatchers.IO) {
                val searchRequest = SearchRequest(q = query, gl = "vn", page = page)

                // Optionally, you can cache the search result in Redis
                val cacheKey = "search:$query:$page"
                val cachedResponse = withContext(Dispatchers.IO) { jedis.get(cacheKey) }
                if (cachedResponse != null) {
                    // Deserialize the cached response and return it
                    return@withContext gson.fromJson(cachedResponse, SearchResponse::class.java)
                }

                val response = searchApi.searchImages(searchRequest)
                Log.d("SearchService", "Search response: $cacheKey")

                // Cache the response in Redis
                withContext(Dispatchers.IO) { jedis.set(cacheKey, gson.toJson(response)) }

                return@withContext response
            }
        } catch (e: Exception) {
            e.message?.let { Log.e("SearchService", it) }
            throw e
        }
    }

    suspend fun autocomplete(query: String): AutoCompleteResponse {
        val searchRequest = SearchRequest(q = query, gl = "vn", type = "autocomplete")
        val cacheKey = "autocomplete:$query"
        val cachedResponse = jedis.get(cacheKey)
        if (cachedResponse != null) {
            return gson.fromJson(cachedResponse, AutoCompleteResponse::class.java)
        }

        val response = searchApi.autocomplete(searchRequest)
        jedis.set(cacheKey, gson.toJson(response))

        return response
    }
}