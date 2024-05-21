package org.easyprog.musicapp.ui.screens.home.uicomponents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil3.compose.AsyncImage
import custom_elements.text.DefaultText
import data.model.Song
import org.easyprog.musicapp.ui.screens.home.NameCategory
import kotlin.math.absoluteValue

@Composable
fun LastSongsComponent(
    modifier: Modifier = Modifier,
    lastSongsList: List<Song>,
    playSong: (song: Song, index: Int) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        NameCategory(text = "Новые 10 песен")
        HorizontalPagerLastSongs(songList = lastSongsList, playSong = playSong::invoke)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HorizontalPagerLastSongs(
    modifier: Modifier = Modifier,
    songList: List<Song>,
    playSong: (song: Song, index: Int) -> Unit
) {
    val horizontalState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0F,
        pageCount = { songList.size })
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val pageSize = (screenWidth / 2F).dp

    HorizontalPager(
        modifier = modifier.fillMaxWidth(),
        state = horizontalState,
        contentPadding = PaddingValues(horizontal = pageSize / 2),
        beyondBoundsPageCount = 1,
        key = { songList[it].id }
    ) { indexSong ->
        LastSongItem(
            modifier = Modifier.size(pageSize)
                .clickable { playSong(songList[indexSong], indexSong) },
            song = songList[indexSong],
            pagerState = horizontalState,
            indexPage = indexSong
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LastSongItem(
    modifier: Modifier = Modifier,
    song: Song,
    pagerState: PagerState,
    indexPage: Int
) {
    val pageOffset = (pagerState.currentPage - indexPage) + pagerState.currentPageOffsetFraction
    var visibleNameSong by remember { mutableStateOf(false) }
    visibleNameSong = pagerState.currentPage == indexPage

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .graphicsLayer {
                alpha =
                    lerp(
                        start = 0.4f,
                        stop = 1f,
                        fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f),
                    )

                cameraDistance = 8 * density
                rotationY =
                    lerp(
                        start = 0f,
                        stop = 30f,
                        fraction = pageOffset.coerceIn(-1f, 1f),
                    )

                lerp(
                    start = 0.8f,
                    stop = 1f,
                    fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f),
                ).also { scale ->
                    scaleX = scale
                    scaleY = scale
                }
            },
        contentAlignment = Alignment.BottomStart
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = song.urlImage,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        AnimatedVisibility(
            visible = visibleNameSong,
            enter = slideInHorizontally(),
            exit = fadeOut()
        ) {
            NameSong(modifier = Modifier, title = song.title)
        }
    }
}

@Composable
private fun NameSong(modifier: Modifier = Modifier, title: String) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp).padding(bottom = 16.dp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DefaultText(
            modifier = Modifier.weight(1F),
            text = title,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start,
            fontSize = 20.sp
        )

        Icon(
            modifier = Modifier.size(40.dp),
            imageVector = Icons.Default.PlayCircleOutline,
            contentDescription = null,
            tint = Color.White
        )
    }
}