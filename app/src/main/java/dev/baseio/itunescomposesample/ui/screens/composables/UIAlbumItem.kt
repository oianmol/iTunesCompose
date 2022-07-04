package dev.baseio.itunescomposesample.ui.screens.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.baseio.itunescomposesample.ui.screens.ITunesAlbumUI
import dev.baseio.itunesdomain.models.DMiTunesAlbum

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UIAlbumItem(album: DMiTunesAlbum, onFavPressed: () -> Unit) {
    ITunesAlbumUI(modifier = Modifier.padding(8.dp), album = album) {
        onFavPressed()
    }
}