package com.example.androidtest.service

import com.example.androidtest.model.SearchApi
import com.example.androidtest.model.request.SearchRequest
import com.example.androidtest.model.response.AutoCompleteResponse
import com.example.androidtest.model.response.SearchResponse
import com.google.gson.Gson
import org.springframework.data.redis.core.RedisTemplate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchService @Inject constructor(
    private val searchApi: SearchApi,
    private val redisTemplate: RedisTemplate<String, Any>,
    private val gson: Gson
) {
    suspend fun search(query: String, page: Int): SearchResponse {
        val cacheKey = "search:$query:$page"
        val cachedResponse = getCachedResponse<SearchResponse>(cacheKey)

        return if (cachedResponse != null) {
            cachedResponse
        } else {
            val searchRequest = SearchRequest(q = query, gl = "vn", page = page)
            val response = searchApi.searchImages(searchRequest)
            cacheResponse(cacheKey, response)
            response
        }
    }

    suspend fun autocomplete(query: String): AutoCompleteResponse {
        val cacheKey = "autocomplete:$query"
        val cachedResponse = getCachedResponse<AutoCompleteResponse>(cacheKey)

        return if (cachedResponse != null) {
            cachedResponse
        } else {
            val searchRequest = SearchRequest(q = query, gl = "vn", type = "autocomplete")
            val response = searchApi.autocomplete(searchRequest)
            cacheResponse(cacheKey, response)
            response
        }
    }

    private inline fun <reified T> getCachedResponse(key: String): T? {
        val cachedData = redisTemplate.opsForValue().get(key)
        return cachedData?.let {
            gson.fromJson(it.toString(), T::class.java)
        }
    }

    private fun cacheResponse(key: String, data: Any) {
        redisTemplate.opsForValue().set(key, gson.toJson(data))
    }
}