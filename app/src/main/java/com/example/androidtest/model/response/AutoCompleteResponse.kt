package com.example.androidtest.model.response

data class SuggestionValue(
    val value: String
)

data class AutoCompleteResponse(
    val searchParameters: SearchParameters,
    val suggestions: List<SuggestionValue>
)