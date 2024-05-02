package org.easyprog.musicapp.screens.player_view

import android.graphics.Paint.Align
import android.util.Log
import android.widget.ProgressBar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil3.compose.AsyncImage
import custom_elements.slider.customSliderColors
import custom_elements.text.DefaultText
import data.SongsViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PlayerComponent(
    modifier: Modifier = Modifier,
    viewModel: SongsViewModel
) {
    val indexSong by viewModel.indexSong.collectAsState()
    val currentTime by viewModel.currentTime.collectAsState()
    val songsList by viewModel.songsListFLow.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val fullTime by viewModel.fullTimeSong.collectAsState()

    if (songsList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color(0xFF121212)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Загрузка...",
                fontSize = 20.sp,
                color = Color.White
            )
        }
    } else {
        Column(
            modifier = modifier.fillMaxSize().background(Color(0xFF121212)).padding(top = 16.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val pagerState = rememberPagerState(pageCount = { songsList.size })
            val coroutineScope = rememberCoroutineScope()

            HorizontalPager(
                modifier = Modifier.padding(top = 40.dp).fillMaxWidth().height(300.dp),
                state = pagerState,
                pageSpacing = 16.dp,
                beyondBoundsPageCount = 2,
                contentPadding = PaddingValues(horizontal = 40.dp),
                userScrollEnabled = false
            ) { page ->
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        modifier = Modifier.fillMaxHeight().align(Alignment.Center).clip(RoundedCornerShape(16.dp))
                            .graphicsLayer {
                                val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                                alpha = lerp(
                                    start = 0.5F,
                                    stop = 1F,
                                    fraction = 1F - pageOffset.coerceIn(0F, 1F)
                                )
                            },
                        model = songsList[page].urlImage,
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Column(
                modifier = Modifier.padding(vertical = 60.dp, horizontal = 32.dp)
            ) {
                val interactionSource = remember { MutableInteractionSource() }

                Spacer(modifier = Modifier.weight(1F))

                Column(
                    modifier = Modifier.padding(start = 4.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    DefaultText(
                        text = songsList[indexSong].title,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = TextUnit(-0.5F, TextUnitType.Sp),
                        fontSize = 20.sp
                    )
                    DefaultText(
                        text = songsList[indexSong].artist,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        letterSpacing = TextUnit(-0.5F, TextUnitType.Sp),
                        fontSize = 20.sp
                    )
                }

                Slider(
                    modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
                    value = currentTime,
                    onValueChange = { viewModel.changeTime(it) },
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

                Row(
                    modifier = Modifier.padding(start = 4.dp, bottom = 16.dp).fillMaxWidth()
                ) {
                    DefaultText(
                        text = "${TimeUnit.MILLISECONDS.toMinutes(currentTime.toLong())}:${
                            if (TimeUnit.MILLISECONDS.toSeconds(currentTime.toLong()) % 60 > 9) (TimeUnit.MILLISECONDS.toSeconds(currentTime.toLong()) % 60)
                            else (TimeUnit.MILLISECONDS.toSeconds(currentTime.toLong()) % 60).toString().padStart(2, '0')
                        }",
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        letterSpacing = TextUnit(-0.5F, TextUnitType.Sp),
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.weight(1F))

                    DefaultText(
                        text = "${TimeUnit.MILLISECONDS.toMinutes(fullTime)}:${
                            if (TimeUnit.MILLISECONDS.toSeconds(fullTime) % 60 > 9) (TimeUnit.MILLISECONDS.toSeconds(fullTime) % 60)
                            else (TimeUnit.MILLISECONDS.toSeconds(fullTime) % 60).toString().padStart(2, '0')
                        }",
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        letterSpacing = TextUnit(-0.5F, TextUnitType.Sp),
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.weight(1F))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                ) {
                    Icon(
                        modifier = Modifier.size(50.dp)
                            .clickable {
                                viewModel.prevSong()
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(indexSong)
                                }
                            },
                        imageVector = Icons.Default.SkipPrevious,
                        contentDescription = null,
                        tint = if (indexSong == 0) Color.Gray else Color.White
                    )

                    Icon(
                        modifier = Modifier.size(70.dp)
                            .clickable {
                                viewModel.pauseOrPlay()
                            },
                        imageVector = if (isPlaying) Icons.Filled.PauseCircle else Icons.Filled.PlayCircle,
                        contentDescription = null,
                        tint = Color.White
                    )

                    Icon(
                        modifier = Modifier.size(50.dp)
                            .clickable {
                                viewModel.nextSong()
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(indexSong)
                                }
                            },
                        imageVector = Icons.Filled.SkipNext,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.weight(1F))
            }
        }
    }
}