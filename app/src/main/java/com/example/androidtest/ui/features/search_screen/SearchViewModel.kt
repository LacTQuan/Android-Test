package com.example.androidtest.ui.features.search_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtest.service.SearchService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchService: SearchService) : ViewModel() {
    private var lastQueryTime: Long = 0
    var state by mutableStateOf(
        SearchContract.State(
            searchResults = null,
            query = "",
            isActive = false,
            suggestions = emptyList(),
            page = 1,
            isLoading = false
        )
    )
        private set

    private var effects = Channel<SearchContract.Effect>(UNLIMITED)


    fun onQueryChange(query: String) {
        state = state.copy(query = query)
        if (state.isActive) {
            if (query.isEmpty()) {
                state = state.copy(suggestions = emptyList())
            } else if (System.currentTimeMillis() - lastQueryTime > 200) {
                lastQueryTime = System.currentTimeMillis()
//                viewModelScope.launch {
//                    performAutocomplete()
//                }
            }
        }
    }

    fun onActiveChange(isActive: Boolean) {
        state = state.copy(isActive = isActive)
        if (isActive) {
            lastQueryTime = System.currentTimeMillis()
        }
    }

    fun onSearch(query: String) {
        if (query.isNotEmpty()) {
            state = state.copy(query = query)
            viewModelScope.launch {
                performSearch()
            }
        }
    }

    fun loadMore() {
        state = state.copy(page = state.page + 1)
        viewModelScope.launch {
            performSearch()
        }
    }

    private suspend fun performSearch() {
        state = state.copy(isActive = false, isLoading = true)
        val searchResponse = searchService.search(state.query, state.page)
        if (state.page == 1) {
            state = state.copy(searchResults = searchResponse, isLoading = false)
        } else if (state.page > 1) {
            val currentResults = state.searchResults?.images ?: emptyList()
            state = state.copy(searchResults = searchResponse.copy(images = currentResults + searchResponse.images), isLoading = false)
        }
        effects.send(SearchContract.Effect.DataWasLoaded)
    }

    private suspend fun performAutocomplete() {
        val autocompleteResponse = searchService.autocomplete(state.query)
        state = state.copy(suggestions = autocompleteResponse.suggestions)
    }
}