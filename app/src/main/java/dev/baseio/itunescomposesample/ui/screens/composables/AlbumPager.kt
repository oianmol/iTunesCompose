package dev.baseio.itunescomposesample.ui.screens.composables

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import dev.baseio.itunescomposesample.ui.screens.CoilImage
import dev.baseio.itunescomposesample.ui.screens.ITunesAlbumUI
import dev.baseio.itunescomposesample.ui.screens.MediaControls
import dev.baseio.itunesdomain.models.DMiTunesAlbum

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AlbumPager(
    dataSet: List<DMiTunesAlbum>,
    pagerState: PagerState,
    onFavPressed: (String) -> Unit,
    onPalette: (Palette) -> Unit
) {
    HorizontalPager(
        count = dataSet.size, state = pagerState, modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) { page ->
        val album = dataSet[page]
        CoilImage(album, onPalette = {
            onPalette(it)
        })
    }
    if (dataSet.isNotEmpty()) {
        ITunesAlbumUI(
            Modifier
                .padding(horizontal = 12.dp, vertical = 4.dp),
            dataSet[pagerState.currentPage],
            onFavPressed = {
                val album = dataSet[pagerState.currentPage]
                onFavPressed(album.id)
            }
        )
        MediaControls(
            Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}