package com.example.androidtest.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: Any?,
    onQueryChange: Any?,
    onSearch: Any?,
    active: Any?,
    onActiveChange: Any?,
    function: () -> Unit
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = active,
        onActiveChange = onActiveChange
    ) {

    }
}
