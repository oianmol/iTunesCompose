package dev.baseio.itunescomposesample.ui.screens.composables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import dev.baseio.itunesdomain.models.DMiTunesAlbum

@Composable
fun AlbumsList(dataSet: List<DMiTunesAlbum>, onFavPressed: (String) -> Unit) {
    LazyColumn {
        items(dataSet) { album ->
            UIAlbumItem(album) {
                onFavPressed(album.id)
            }
        }
    }
}