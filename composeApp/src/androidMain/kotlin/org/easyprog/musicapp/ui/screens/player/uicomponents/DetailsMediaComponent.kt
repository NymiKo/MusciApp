package org.easyprog.musicapp.ui.screens.player.uicomponents

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil3.compose.AsyncImage
import custom_elements.slider.customSliderColors
import custom_elements.text.DefaultText
import custom_elements.text.TimeText
import data.model.Song
import org.easyprog.musicapp.ui.theme.Purple
import org.easyprog.musicapp.ui.theme.PurpleDark
import org.easyprog.musicapp.ui.theme.PurpleLight
import utils.toTimeString
import kotlin.math.absoluteValue

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