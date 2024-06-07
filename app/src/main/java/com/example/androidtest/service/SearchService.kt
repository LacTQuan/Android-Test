package com.example.androidtest.service

import android.util.Log
import com.example.androidtest.model.SearchApi
import com.example.androidtest.model.request.SearchRequest
import com.example.androidtest.model.response.AutoCompleteResponse
import com.example.androidtest.model.response.SearchResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import javax.inject.Inject
import javax.inject.Singleton

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

                val cacheKey = "search:$query:$page"
                var cachedResponse: String? = null

                // Attempt to get cached response from Redis
                try {
                    cachedResponse = jedis.get(cacheKey)
                } catch (redisException: Exception) {
                    Log.e(
                        "SearchService",
                        "Failed to get cached response from Redis: $redisException"
                    )
                }

                if (cachedResponse != null) {
                    return@withContext gson.fromJson(cachedResponse, SearchResponse::class.java)
                }

                // If no cached response found, call searchApi
                val response = searchApi.searchImages(searchRequest)
                Log.d("SearchService", "Search response: $cacheKey")

                try {
                    jedis.set(cacheKey, gson.toJson(response))
                } catch (redisException: Exception) {
                    Log.e("SearchService", "Failed to cache response in Redis: $redisException")
                }

                return@withContext response
            }
        } catch (e: Exception) {
            e.message?.let { Log.e("SearchService", it) }
            return searchApi.searchImages(SearchRequest(q = query, gl = "vn", page = page))
        }
    }

    suspend fun autocomplete(query: String): AutoCompleteResponse {
        try {
            return withContext(Dispatchers.IO) {
                val searchRequest = SearchRequest(q = query, gl = "vn", type = "autocomplete")

                val cacheKey = "autocomplete:$query"
                var cachedResponse: String? = null

                // Attempt to get cached response from Redis
                try {
                    cachedResponse = jedis.get(cacheKey)
                } catch (redisException: Exception) {
                    Log.e(
                        "AutocompleteService",
                        "Failed to get cached response from Redis: $redisException"
                    )
                }

                if (cachedResponse != null) {
                    return@withContext gson.fromJson(cachedResponse, AutoCompleteResponse::class.java)
                }

                // If no cached response found, call searchApi
                val response = searchApi.autocomplete(searchRequest)
                Log.d("AutocompleteService", "Autocomplete response: $cacheKey")

                try {
                    jedis.set(cacheKey, gson.toJson(response))
                } catch (redisException: Exception) {
                    Log.e("AutocompleteService", "Failed to cache response in Redis: $redisException")
                }

                return@withContext response
            }
        } catch (e: Exception) {
            e.message?.let { Log.e("SearchService", it) }
            return searchApi.autocomplete(SearchRequest(q = query, gl = "vn", type = "autocomplete"))
        }
    }
}