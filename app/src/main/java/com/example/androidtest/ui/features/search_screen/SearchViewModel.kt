package com.example.androidtest.ui.features.search_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.androidtest.service.SearchService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchService: SearchService) : ViewModel() {
    var state by mutableStateOf(
        SearchContract.State(
            searchResults = null,
            query = "",
            isActive = false,
            suggestions = emptyList()
        )
    )
        private set

    var effects = Channel<SearchContract.Effect>(UNLIMITED)
        private set


    fun onQueryChange(query: String) {
        state = state.copy(query = query)
    }

    fun onActiveChange(isActive: Boolean) {
        state = state.copy(isActive = isActive)
    }

    suspend fun onSearch() {
        val searchResponse = searchService.search(state.query)
        viewModelScope.launch {
            state = state.copy(searchResults = searchResponse, isActive = false)
            effects.send(SearchContract.Effect.DataWasLoaded)
        }
    }
}