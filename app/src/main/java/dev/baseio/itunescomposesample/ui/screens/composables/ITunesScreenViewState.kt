package dev.baseio.itunescomposesample.ui.screens.composables

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import dev.baseio.itunescomposesample.R
import dev.baseio.itunescomposesample.ui.screens.ITunesScreenVM
import dev.baseio.itunescomposesample.ui.theme.Typography

@Composable
fun ITunesScreenViewState(
    iTunesViewState: ITunesScreenVM.ITunesViewState,
    modifier: Modifier
) {
    when (iTunesViewState) {
        is ITunesScreenVM.ITunesViewState.Loading -> {
            Text(
                text = if (iTunesViewState.isLoading) stringResource(
                    R.string.loading_albums
                ) else "Top Albums",
                style = Typography.h5, modifier = modifier
            )
        }
        is ITunesScreenVM.ITunesViewState.Exception -> {
            Text(
                text = (iTunesViewState as ITunesScreenVM.ITunesViewState.Exception).throwable.localizedMessage
                    ?: "Failed with unknown Exception",
                style = Typography.h5.copy(color = Color.Red.copy(alpha = 0.8f)),
                modifier = modifier
            )
        }
        ITunesScreenVM.ITunesViewState.Empty -> {
            CircularProgressIndicator(modifier)
        }
    }
}
