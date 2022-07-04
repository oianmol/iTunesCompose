package dev.baseio.itunescomposesample.ui.screens.composables

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.baseio.itunescomposesample.ui.screens.ITunesScreenVM

@Composable
fun ColumnScope.TopAlbumsHeader(
    state: ITunesScreenVM.ITunesHeaderViewState?,
    itunesAlbumSearch: String?,
    iTunesViewState: ITunesScreenVM.ITunesViewState,
    searchModeSwitch: () -> Unit,
    onSearch: (String) -> Unit
) {

    AnimatedVisibility(
        visible = state == ITunesScreenVM.ITunesHeaderViewState.ActiveSearchMode,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        Row(Modifier.padding(8.dp)) {
            TextField(value = itunesAlbumSearch ?: "", onValueChange = { search ->
                onSearch(search)
            }, leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null)
            }, colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ))
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                searchModeSwitch()
            }) {
                Icon(Icons.Default.Close, contentDescription = null)
            }
        }
    }

    AnimatedVisibility(
        visible = state == ITunesScreenVM.ITunesHeaderViewState.SearchIconMode,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        Row(Modifier.padding(8.dp)) {
            ITunesScreenViewState(
                iTunesViewState,
                Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                searchModeSwitch()
            }) {
                Icon(Icons.Default.Search, contentDescription = null)
            }
        }
    }

}