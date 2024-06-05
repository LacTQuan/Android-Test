package com.example.androidtest.ui.features.search_screen

import com.example.androidtest.model.response.SearchResponse

class SearchContract {
    data class State(
        val query: String = "",
        val searchResults: SearchResponse? = null,
        val isActive: Boolean = false,
        val suggestions: List<String> = emptyList()
    )

    sealed class Effect {
        object DataWasLoaded : Effect()
    }
}