package com.example.androidtest.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidtest.model.response.SuggestionValue
import com.example.androidtest.ui.theme.Primary

@Composable
fun MySearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val interactionSource = remember { MutableInteractionSource() }
    val isTextFieldFocused by interactionSource.collectIsFocusedAsState()

    if (isTextFieldFocused) {
        onActiveChange(true)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(8.dp)
            .padding(end = 16.dp)
            .background(Color.Transparent, shape = MaterialTheme.shapes.small)
            .border(3.dp, Primary, MaterialTheme.shapes.small)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = query,
                onValueChange = {
                    onQueryChange(it)
                    expanded = it.isNotEmpty()
                },
                modifier = Modifier
                    .weight(1f),
                textStyle = TextStyle(fontSize = 16.sp),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(0.dp)
                    ) {
                        if (query.isEmpty()) {
                            Text(text = "Type something to search", color = Color.Gray, fontSize = 16.sp)
                        }
                        else {
                            innerTextField()
                        }
                    }
                },
                interactionSource = interactionSource
            )
            IconButton(
                onClick = {
                    focusManager.clearFocus()
                    onSearch(query)
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search icon"
                )
            }
        }
    }
}

@Composable
fun DropdownMenu(
    active: Boolean,
    suggestions: List<SuggestionValue>,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit,
    focusManager: FocusManager = LocalFocusManager.current,
    query: String
) {
    if (active && query.isNotEmpty()) {
        Log.d("DropdownMenu", "active: $active")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            LazyColumn(
                modifier = Modifier.padding(8.dp)
                    .padding(start = 16.dp)
            ) {
                items(suggestions.size) { index ->
                    Text(
                        text = suggestions[index].value,
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable(onClick = {
                                focusManager.clearFocus()
                                onQueryChange(suggestions[index].value)
                                onActiveChange(false)
                                onSearch(suggestions[index].value)
                            })
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MySearchBarPreview() {
    MySearchBar(
        query = "",
        onQueryChange = {},
        onSearch = {},
        active = false,
        onActiveChange = {}
    )
}
