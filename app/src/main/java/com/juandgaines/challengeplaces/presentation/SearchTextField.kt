package com.juandgaines.challengeplaces.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.juandgaines.challengeplaces.R

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    onClearClick: () -> Unit,
) {
    val textFieldDescription = stringResource(R.string.search_field_description)
    val textFieldClear = stringResource(R.string.search_field_clear)

    OutlinedTextField(
        modifier = modifier.fillMaxWidth()
            .semantics {
                contentDescription = textFieldDescription
            },
        value = text,
        onValueChange = { newText ->
            onTextChange(newText)
        },
        placeholder = { Text("Search") },
        trailingIcon = {
            if (text.isNotEmpty()) {
                IconButton(onClick = onClearClick) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = textFieldClear
                    )
                }
            }
        },
    )
}