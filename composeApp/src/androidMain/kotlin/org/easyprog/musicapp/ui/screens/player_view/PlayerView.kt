package org.easyprog.musicapp.ui.screens.player_view

import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Loop
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import coil3.compose.AsyncImage
import custom_elements.slider.customSliderColors
import custom_elements.text.DefaultText
import custom_elements.text.TimeText
import data.MediaViewModel
import data.model.Song
import kotlinx.coroutines.launch
import org.easyprog.musicapp.ui.Purple
import org.easyprog.musicapp.ui.PurpleDark
import org.easyprog.musicapp.ui.PurpleLight
import utils.toMinutes
import utils.toSeconds
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PlayerComponent(
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: MediaViewModel
) {
    val currentPlayingSongIndex by viewModel.currentPlayingSongIndex.collectAsState()
    val currentTime by viewModel.currentTime.collectAsState()
    val songsList by viewModel.songsListFLow.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val fullTime by viewModel.fullTimeSong.collectAsState()

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                viewModel.releasePlayer()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if (songsList.isEmpty()) {
        BoxLoading()
    } else {
        PlayerScreen(
            modifier = modifier,
            songsList = songsList,
            currentPlayingSongIndex = currentPlayingSongIndex,
            currentTime = currentTime,
            fullTime = fullTime,
            isPlaying = isPlaying,
            changeTime = viewModel::changeTime,
            prevSong = viewModel::prevSong,
            pauseOrPlay = viewModel::pauseOrPlay,
            nextSong = viewModel::nextSong,
            scrollToSong = viewModel::scrollToSong
        )
    }
}

@Composable
private fun BoxLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Загрузка...",
            fontSize = 20.sp,
            color = Color.White
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PlayerScreen(
    modifier: Modifier = Modifier,
    songsList: List<Song>,
    currentPlayingSongIndex: Int,
    currentTime: Float,
    fullTime: Long,
    isPlaying: Boolean,
    changeTime: (time: Float) -> Unit,
    prevSong: () -> Unit,
    pauseOrPlay: () -> Unit,
    nextSong: () -> Unit,
    scrollToSong: (page: Int) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val pagerState = rememberPagerState(pageCount = { songsList.size })

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                scrollToSong(page)
            }
        }

        LaunchedEffect(currentPlayingSongIndex) {
            pagerState.animateScrollToPage(currentPlayingSongIndex)
        }

        DetailsMediaComponent(
            pagerState = pagerState,
            songsList = songsList,
            currentTime = currentTime,
            currentPlayingSongIndex = currentPlayingSongIndex,
            fullTime = fullTime,
            changeTime = changeTime::invoke
        )

        Spacer(modifier = Modifier.weight(1F))

        PlayerControlRow(
            currentPlayingSongIndex = currentPlayingSongIndex,
            isPlaying = isPlaying,
            pagerState = pagerState,
            nextSong = nextSong::invoke,
            pauseOrPlay = pauseOrPlay::invoke,
            prevSong = prevSong::invoke
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailsMediaComponent(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    songsList: List<Song>,
    currentTime: Float,
    currentPlayingSongIndex: Int,
    fullTime: Long,
    changeTime: (time: Float) -> Unit
) {
    Column(
        modifier = modifier.clip(RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
            .background(Brush.verticalGradient(listOf(PurpleLight, PurpleDark)))
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        ArtworkMediaHorizontalPager(
            pagerState = pagerState,
            artworkUrls = songsList.map { it.urlImage }
        )

        RowTimeAndNameDetailMedia(
            currentTime = currentTime,
            fullTime = fullTime,
            song = songsList[currentPlayingSongIndex]
        )

        TimeMediaSlider(
            currentTime = currentTime,
            fullTime = fullTime,
            changeTime = changeTime::invoke
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArtworkMediaHorizontalPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    artworkUrls: List<String>
) {
    HorizontalPager(
        modifier = modifier.padding(top = 16.dp).fillMaxWidth().height(200.dp),
        state = pagerState,
        pageSpacing = 16.dp,
        beyondBoundsPageCount = 2,
        contentPadding = PaddingValues(horizontal = 80.dp)
    ) { page ->
        ArtworkMediaItem(
            pagerState = pagerState,
            page = page,
            artworkUrl = artworkUrls[page]
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArtworkMediaItem(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    page: Int,
    artworkUrl: String
) {
    Box(modifier = modifier.fillMaxSize()) {
        AsyncImage(
            modifier = Modifier.fillMaxHeight().align(Alignment.Center)
                .clip(CircleShape)
                .graphicsLayer {
                    val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                    alpha = lerp(
                        start = 0.5F,
                        stop = 1F,
                        fraction = 1F - pageOffset.coerceIn(0F, 1F)
                    )
                },
            model = artworkUrl,
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun RowTimeAndNameDetailMedia(
    modifier: Modifier = Modifier,
    currentTime: Float,
    fullTime: Long,
    song: Song
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TimeText(
            text = "${currentTime.toLong().toMinutes()}:${
                currentTime.toLong().toSeconds().toString().padStart(2, '0')
            }"
        )

        ArtistAndNameMediaColumn(modifier = Modifier.weight(1F), song = song)

        TimeText(
            text = "${fullTime.toMinutes()}:${fullTime.toSeconds().toString().padStart(2, '0')}"
        )
    }
}

@Composable
fun ArtistAndNameMediaColumn(
    modifier: Modifier = Modifier,
    song: Song
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DefaultText(
            text = song.title,
            color = MaterialTheme.colorScheme.secondary,
            letterSpacing = TextUnit(-0.5F, TextUnitType.Sp),
            fontSize = 20.sp
        )

        DefaultText(
            text = song.artist,
            color = Color.Gray,
            letterSpacing = TextUnit(-0.5F, TextUnitType.Sp),
            fontSize = 20.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeMediaSlider(
    modifier: Modifier = Modifier,
    currentTime: Float,
    fullTime: Long,
    changeTime: (time: Float) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Slider(
        modifier = Modifier.padding(horizontal = 24.dp).fillMaxWidth(),
        value = currentTime,
        onValueChange = { changeTime(it) },
        interactionSource = interactionSource,
        valueRange = 0F..fullTime.toFloat(),
        track = { sliderState ->
            SliderDefaults.Track(
                modifier = Modifier.scale(scaleX = 1F, scaleY = 0.5F),
                sliderState = sliderState,
                colors = customSliderColors(
                    activeTrackColor = Color.White,
                    inactiveTrackColor = Color.Gray.copy(alpha = 0.4F)
                )
            )
        },
        thumb = {
            SliderDefaults.Thumb(
                modifier = Modifier.clip(CircleShape).padding(4.dp),
                interactionSource = interactionSource,
                thumbSize = DpSize(width = 10.dp, height = 10.dp),
                colors = customSliderColors(
                    thumbColor = Color.White
                )
            )
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayerControlRow(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    currentPlayingSongIndex: Int,
    isPlaying: Boolean,
    prevSong: () -> Unit,
    pauseOrPlay: () -> Unit,
    nextSong: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = modifier.padding(8.dp).fillMaxWidth().shadow(4.dp, CircleShape)
            .background(MaterialTheme.colorScheme.primary, CircleShape).padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterHorizontally)
    ) {
        Icon(
            modifier = Modifier.size(25.dp)
                .clickable {

                },
            imageVector = Icons.Default.Loop,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )

        Icon(
            modifier = Modifier.size(35.dp)
                .clickable {
                    prevSong()
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(currentPlayingSongIndex)
                    }
                },
            imageVector = Icons.Default.SkipPrevious,
            contentDescription = null,
            tint = if (currentPlayingSongIndex == 0) Color.Gray else MaterialTheme.colorScheme.secondary
        )

        Icon(
            modifier = Modifier.size(65.dp)
                .clickable {
                    pauseOrPlay()
                },
            imageVector = if (isPlaying) Icons.Filled.PauseCircle else Icons.Filled.PlayCircle,
            contentDescription = null,
            tint = Purple
        )

        Icon(
            modifier = Modifier.size(35.dp)
                .clickable {
                    nextSong()
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(currentPlayingSongIndex)
                    }
                },
            imageVector = Icons.Filled.SkipNext,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )

        Icon(
            modifier = Modifier.size(25.dp)
                .clickable {

                },
            imageVector = Icons.Default.Shuffle,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}