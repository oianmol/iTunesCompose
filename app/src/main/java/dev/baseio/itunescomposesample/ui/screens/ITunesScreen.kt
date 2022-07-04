package dev.baseio.itunescomposesample.ui.screens

import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.palette.graphics.Palette
import coil.compose.AsyncImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.baseio.itunescomposesample.R
import dev.baseio.itunescomposesample.ui.screens.composables.AlbumPager
import dev.baseio.itunescomposesample.ui.screens.composables.AlbumsList
import dev.baseio.itunescomposesample.ui.screens.composables.TopAlbumsHeader
import dev.baseio.itunescomposesample.ui.theme.Typography
import dev.baseio.itunesdomain.models.DMiTunesAlbum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ITunesScreen() {
    val viewModel: ITunesScreenVM = hiltViewModel()
    Scaffold {
        ITunesContent(Modifier.padding(it), viewModel)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ITunesContent(modifier: Modifier, viewModel: ITunesScreenVM) {
    val carouselDataSet by viewModel.iTunesDataSet.collectAsState()
    val iTunesSearchDataSet by viewModel.iTunesSearchDataSet.collectAsState()
    val iTunesViewState by viewModel.iTunesViewState.collectAsState()
    val iTunesHeaderViewState by viewModel.iTunesHeaderViewState.collectAsState()
    val itunesAlbumSearch by viewModel.itunesAlbumSearch.collectAsState()
    val pagerState = rememberPagerState()
    val systemUiController = rememberSystemUiController()
    var palette by remember {
        mutableStateOf<Palette?>(null)
    }

    SideEffect {
        systemUiController.setStatusBarColor(
            color = palette?.darkMutedSwatch?.rgb?.toComposeColor()
                ?: palette?.darkVibrantSwatch?.rgb?.toComposeColor() ?: Color.Black,
            darkIcons = false
        )
        systemUiController.setNavigationBarColor(Color.Black)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        if (carouselDataSet.isNotEmpty()) {
            BleedAlbumColor(palette)
        }

        Column {
            TopAlbumsHeader(
                iTunesHeaderViewState,
                itunesAlbumSearch,
                iTunesViewState, {
                    viewModel.searchModeSwitch()
                }
            ) { search ->
                viewModel.onSearch(search)
            }

            if (iTunesHeaderViewState is ITunesScreenVM.ITunesHeaderViewState.ActiveSearchMode) {
                AlbumsList(iTunesSearchDataSet, onFavPressed = {
                    viewModel.onFavPressed(it)
                })
            } else {
                AlbumPager(carouselDataSet, pagerState, { id ->
                    viewModel.onFavPressed(id)
                }, onPalette = { palette1 ->
                    palette = palette1
                })
            }


        }

    }
}

@Composable
fun MediaControls(modifier: Modifier) {
    Column(modifier) {
        SliderAudio()
        PlayerControlOptions()
    }
}

@Composable
fun PlayerControlOptions() {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_shuffle_24),
                contentDescription = null, tint = Color.White, modifier = Modifier.size(36.dp)
            )
        }

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_skip_previous_24),
                contentDescription = null, tint = Color.White, modifier = Modifier.size(36.dp)
            )
        }

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_play_circle_filled_24),
                contentDescription = null, tint = Color.White, modifier = Modifier.size(36.dp)
            )
        }

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_skip_next_24),
                contentDescription = null, tint = Color.White, modifier = Modifier.size(36.dp)
            )
        }

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_repeat_24),
                contentDescription = null, tint = Color.White, modifier = Modifier.size(36.dp)
            )
        }
    }
}

@Composable
fun SliderAudio() {
    var sliderPosition by remember { mutableStateOf(0f) }
    Slider(
        value = sliderPosition,
        onValueChange = { sliderPosition = it },
        valueRange = 0f..100f,
        onValueChangeFinished = {
        },
        colors = SliderDefaults.colors(
            thumbColor = Color.White,
            activeTrackColor = Color.White.copy(alpha = 0.4f)
        )
    )
}

private fun Int.toComposeColor(): Color {
    return Color(this)
}

@Composable
fun CoilImage(album: DMiTunesAlbum, onPalette: (Palette) -> Unit) {

    val painter = rememberImagePainter(data = album.albumArt, builder = {
        allowHardware(false)
    })
    val painterState = painter.state

    if (painterState is AsyncImagePainter.State.Success) {
        LaunchedEffect(key1 = painter) {
            launch(Dispatchers.IO) {
                val image = painter.imageLoader.execute(painter.request).drawable
                val bitmap = (image as BitmapDrawable).bitmap
                Palette.from(bitmap)
                    .generate { palette ->
                        palette?.let {
                            onPalette(palette)
                        }
                    }
            }
        }
    }

    Image(
        painter = painter,
        contentDescription = null,
        Modifier
            .size(400.dp)
            .padding(horizontal = 8.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun BleedAlbumColor(palette: Palette?) {
    var topColor: Color = Color.Black
    palette?.let {
        it.darkMutedSwatch?.rgb?.let { color ->
            topColor = Color(color)
        } ?: run {
            it.darkVibrantSwatch?.rgb?.let { color ->
                topColor = Color(color)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        topColor, Color.Black
                    )
                )
            )
    )
}

@Composable
fun ITunesAlbumUI(modifier: Modifier, album: DMiTunesAlbum, onFavPressed: () -> Unit) {
    Row(
        verticalAlignment = Alignment.Bottom, modifier = modifier
    ) {
        AlbumDetails(modifier = Modifier, album, onFavPressed = {
            onFavPressed()
        })
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlbumDetails(modifier: Modifier, album: DMiTunesAlbum, onFavPressed: () -> Unit) {
    ListItem(text = {
        Text(
            album.label,
            style = Typography.h6.copy(color = Color.White, fontWeight = FontWeight.Bold),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }, secondaryText = {
        Column {
            Text(
                album.artistName,
                style = Typography.subtitle1.copy(color = Color.White),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = album.price,
                style = Typography.subtitle2.copy(color = Color.White),
                maxLines = 1
            )
        }
    }, trailing = {
        IconButton(onClick = {
            onFavPressed()
        }) {
            Icon(
                imageVector = if (album.isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = null,
                tint = if (album.isFav) Color.Red else Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }, modifier = modifier)
}


