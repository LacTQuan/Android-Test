package com.example.androidtest.ui.features.search_screen

import com.example.androidtest.model.response.SearchResponse
import com.example.androidtest.model.response.SuggestionValue

class SearchContract {
    data class State(
        val query: String = "",
        val searchResults: SearchResponse? = null,
        val isActive: Boolean = false,
        val suggestions: List<SuggestionValue>? = emptyList(),
        val page: Int = 0,
        val isLoading: Boolean = false
    )

    sealed class Effect {
        object DataWasLoaded : Effect()
    }
}