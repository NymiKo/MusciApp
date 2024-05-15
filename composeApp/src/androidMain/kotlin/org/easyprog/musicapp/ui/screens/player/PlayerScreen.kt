package org.easyprog.musicapp.ui.screens.player

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Loop
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import audio_player.AudioPlayerState
import audio_player.AudioPlayerUiState
import coil3.compose.AsyncImage
import custom_elements.slider.customSliderColors
import custom_elements.text.DefaultText
import custom_elements.text.TimeText
import ui.player.MediaViewModel
import data.model.Song
import org.easyprog.musicapp.ui.theme.Purple
import org.easyprog.musicapp.ui.theme.PurpleDark
import org.easyprog.musicapp.ui.theme.PurpleLight
import utils.toTimeString
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    modifier: Modifier = Modifier,
    audioPlayerUiState: AudioPlayerUiState,
    viewModel: MediaViewModel
) {
    val playerScreenUiState = viewModel.playerUiState

    if (playerScreenUiState.loading) {
        BoxLoading()
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        Icon(
                            modifier = Modifier.padding(8.dp).size(35.dp),
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors()
                        .copy(containerColor = PurpleLight)
                )
            }
        ) { paddingValues ->
            PlayerScreen(
                modifier = modifier.padding(paddingValues),
                songsList = playerScreenUiState.songList,
                currentPlayingSongIndex = audioPlayerUiState.currentPosition,
                currentTime = audioPlayerUiState.currentTime.toFloat(),
                fullTime = audioPlayerUiState.totalTime,
                isPlaying = audioPlayerUiState.playerState == AudioPlayerState.PLAYING,
                isRepeatModeEnabled = audioPlayerUiState.isRepeat,
                isShuffleModeEnabled = audioPlayerUiState.isShuffle,
                changeTime = viewModel::changeTime,
                prevSong = viewModel::prevSong,
                pauseOrPlay = {
                    if (audioPlayerUiState.playerState == AudioPlayerState.PLAYING) viewModel.pause() else viewModel.resume()
                },
                nextSong = viewModel::nextSong,
                scrollToSong = viewModel::scrollToSong,
                changeRepeatMode = viewModel::changeRepeatMode,
                changeShuffleMode = viewModel::changeShuffleMode
            )
        }
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
    isRepeatModeEnabled: Boolean,
    isShuffleModeEnabled: Boolean,
    changeTime: (time: Float) -> Unit,
    prevSong: () -> Unit,
    pauseOrPlay: () -> Unit,
    nextSong: () -> Unit,
    scrollToSong: (page: Int) -> Unit,
    changeRepeatMode: () -> Unit,
    changeShuffleMode: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val pagerState = rememberPagerState(pageCount = { songsList.size })

        DetailsMediaComponent(
            pagerState = pagerState,
            songsList = songsList,
            currentTime = currentTime,
            currentPlayingSongIndex = currentPlayingSongIndex,
            fullTime = fullTime,
            changeTime = changeTime::invoke
        )

        SongsListColumn(
            modifier = Modifier.padding(top = 16.dp).weight(1F),
            songsList = songsList,
            currentPlayingSongIndex = currentPlayingSongIndex,
            scrollToSong = scrollToSong::invoke
        )

        PlayerControlRow(
            currentPlayingSongIndex = currentPlayingSongIndex,
            lastSongIndex = songsList.lastIndex,
            isPlaying = isPlaying,
            isRepeatModeEnabled = isRepeatModeEnabled,
            isShuffleModeEnabled = isShuffleModeEnabled,
            nextSong = nextSong::invoke,
            pauseOrPlay = pauseOrPlay::invoke,
            prevSong = prevSong::invoke,
            changeRepeatMode = changeRepeatMode::invoke,
            changeShuffleMode = changeShuffleMode::invoke
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
            .padding(bottom = 16.dp)
    ) {
        ArtworkMediaHorizontalPager(
            pagerState = pagerState,
            artworkUrls = songsList.map { it.urlImage },
            currentPlayingSongIndex = currentPlayingSongIndex
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
    artworkUrls: List<String>,
    currentPlayingSongIndex: Int
) {
    LaunchedEffect(currentPlayingSongIndex) {
        pagerState.animateScrollToPage(currentPlayingSongIndex)
    }

    HorizontalPager(
        modifier = modifier.padding(vertical = 16.dp).fillMaxWidth().height(200.dp),
        state = pagerState,
        pageSpacing = 1.dp,
        beyondBoundsPageCount = 2
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
                .graphicsLayer {
                    val pageOffset =
                        (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                    translationX = size.width * (pageOffset * .99f)

                    alpha = lerp(
                        start = 0.4f,
                        stop = 1f,
                        fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f),
                    )
                    val blur = (pageOffset * 20f).coerceAtLeast(0.1f)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        renderEffect = RenderEffect
                            .createBlurEffect(
                                blur, blur, Shader.TileMode.DECAL
                            ).asComposeRenderEffect()
                    }

                    lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f),
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }
                }.clip(CircleShape),
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
            text = currentTime.toLong().toTimeString()
        )

        ArtistAndNameMediaColumn(
            modifier = Modifier.weight(1F).height(50.dp),
            song = song,
        )

        TimeText(
            text = fullTime.toTimeString()
        )
    }
}

@Composable
fun ArtistAndNameMediaColumn(
    modifier: Modifier = Modifier,
    song: Song
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
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
        modifier = modifier.padding(horizontal = 24.dp).fillMaxWidth(),
        value = currentTime,
        onValueChange = { changeTime(it) },
        interactionSource = interactionSource,
        valueRange = 0F..fullTime.toFloat(),
        track = { sliderState ->
            SliderDefaults.Track(
                modifier = Modifier.scale(scaleX = 1F, scaleY = 0.5F),
                sliderState = sliderState,
                colors = customSliderColors(
                    activeTrackColor = Purple,
                    inactiveTrackColor = Color.White
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

@Composable
fun SongsListColumn(
    modifier: Modifier = Modifier,
    songsList: List<Song>,
    currentPlayingSongIndex: Int,
    scrollToSong: (page: Int) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(count = songsList.size) { index ->
            SongItem(
                modifier = Modifier.clickable {
                    scrollToSong(index)
                },
                song = songsList[index],
                isPlaying = currentPlayingSongIndex == index
            )
        }
    }
}

@Composable
fun SongItem(
    modifier: Modifier = Modifier,
    song: Song,
    isPlaying: Boolean
) {
    Row(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier.weight(1F),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            DefaultText(
                text = song.artist,
                color = MaterialTheme.colorScheme.secondary,
                letterSpacing = TextUnit(-0.5F, TextUnitType.Sp),
                fontSize = 20.sp,
                textAlign = TextAlign.Start
            )

            DefaultText(
                text = song.title,
                color = Color.Gray,
                letterSpacing = TextUnit(-0.5F, TextUnitType.Sp),
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            )
        }

        NowPlayingSong(modifier = Modifier.align(Alignment.Bottom), isPlaying = isPlaying)
    }
}

@Composable
fun NowPlayingSong(
    modifier: Modifier = Modifier,
    isPlaying: Boolean
) {
    if (isPlaying) {
        DefaultText(
            modifier = modifier,
            text = "Сейчас играет",
            color = Color.White,
            letterSpacing = TextUnit(-0.5F, TextUnitType.Sp),
            fontSize = 16.sp
        )
    } else {
        Icon(
            modifier = modifier.size(20.dp).clip(CircleShape).background(Color.Gray),
            imageVector = Icons.Default.PlayArrow,
            contentDescription = null,
            tint = Color.Black
        )
    }
}

@Composable
fun PlayerControlRow(
    modifier: Modifier = Modifier,
    currentPlayingSongIndex: Int,
    lastSongIndex: Int,
    isPlaying: Boolean,
    isShuffleModeEnabled: Boolean,
    isRepeatModeEnabled: Boolean,
    prevSong: () -> Unit,
    pauseOrPlay: () -> Unit,
    nextSong: () -> Unit,
    changeRepeatMode: () -> Unit,
    changeShuffleMode: () -> Unit
) {
    Row(
        modifier = modifier.padding(8.dp).fillMaxWidth().shadow(4.dp, CircleShape)
            .background(MaterialTheme.colorScheme.primary, CircleShape).padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterHorizontally)
    ) {
        Icon(
            modifier = Modifier.size(25.dp)
                .clickable {
                    changeRepeatMode()
                },
            imageVector = Icons.Default.Loop,
            contentDescription = null,
            tint = if (isRepeatModeEnabled) Purple else MaterialTheme.colorScheme.secondary
        )

        Icon(
            modifier = Modifier.size(35.dp)
                .clickable {
                    prevSong()
                },
            imageVector = Icons.Default.SkipPrevious,
            contentDescription = null,
            tint = if (currentPlayingSongIndex == 0 && !isRepeatModeEnabled) Color.Gray else MaterialTheme.colorScheme.secondary
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
                },
            imageVector = Icons.Filled.SkipNext,
            contentDescription = null,
            tint = if (currentPlayingSongIndex == lastSongIndex && !isRepeatModeEnabled) Color.Gray else MaterialTheme.colorScheme.secondary
        )

        Icon(
            modifier = Modifier.size(25.dp)
                .clickable {
                    changeShuffleMode()
                },
            imageVector = Icons.Default.Shuffle,
            contentDescription = null,
            tint = if (isShuffleModeEnabled) Purple else MaterialTheme.colorScheme.secondary
        )
    }
}