package screens.player_view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import audio_player.AudioPlayerController
import audio_player.AudioPlayerListener
import coil3.compose.AsyncImage
import data.SongsViewModel
import data.model.Song
import koin
import screens.custom_elements.slider.customSliderColors
import screens.custom_elements.text.DefaultText

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PlayerComponent(
    modifier: Modifier = Modifier,
    viewModel: SongsViewModel = koin.get()
) {
    val indexSong = remember { mutableStateOf(0) }
    val audioPlayerController = remember { AudioPlayerController() }
    var currentTime = remember { mutableStateOf(0F) }
    val songsList by viewModel.songsListFLow.collectAsState()

    remember {
        playSong(songsList[indexSong.value], audioPlayerController = audioPlayerController, selectedSongIndex = indexSong, songsList = songsList, currentTime = currentTime)
    }

    val isPlaying = remember { mutableStateOf(false) }

    Box(
        modifier = modifier.fillMaxWidth().height(80.dp)
            .border(0.5.dp, Color(0xFF1B1B1B), RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp)).background(Color(0xFF121212))
    ) {
        Row(
            modifier = Modifier.padding(10.dp).align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier.size(60.dp).clip(RoundedCornerShape(8.dp)),
                model = songsList[indexSong.value].urlImage,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(start = 8.dp),
                verticalArrangement = Arrangement.spacedBy(
                    space = (-4).dp,
                    alignment = Alignment.CenterVertically
                )
            ) {
                DefaultText(
                    text = songsList[indexSong.value].title,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = TextUnit(-0.5F, TextUnitType.Sp)
                )
                DefaultText(
                    text = songsList[indexSong.value].artist,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    letterSpacing = TextUnit(-0.5F, TextUnitType.Sp)
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxHeight().align(Alignment.Center).padding(top = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            var activeTimeMusic by remember { mutableStateOf(false) }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                var activePrevious by remember { mutableStateOf(false) }
                var activePlay by remember { mutableStateOf(false) }
                var activeNext by remember { mutableStateOf(false) }

                Icon(
                    modifier = Modifier.size(30.dp)
                        .onPointerEvent(PointerEventType.Enter) { activePrevious = true }
                        .onPointerEvent(PointerEventType.Exit) { activePrevious = false }
                        .clickable {
                            if (indexSong.value == 0) {
                                indexSong.value = 0
                            } else {
                                indexSong.value -= 1
                            }
                        },
                    imageVector = Icons.Default.SkipPrevious,
                    contentDescription = null,
                    tint = if (activePrevious) Color.White else Color.Gray
                )

                Icon(
                    modifier = Modifier.size(if (activePlay) 47.dp else 45.dp)
                        .onPointerEvent(PointerEventType.Enter) { activePlay = true }
                        .onPointerEvent(PointerEventType.Exit) { activePlay = false }
                        .clickable {
                            if (audioPlayerController.isPlaying()) {
                                audioPlayerController.pause()
                                isPlaying.value = false
                            } else {
                                audioPlayerController.start()
                                isPlaying.value = true
                            }
                        },
                    imageVector = if (isPlaying.value) Icons.Filled.PauseCircle else Icons.Filled.PlayCircle,
                    contentDescription = null,
                    tint = Color.White
                )

                Icon(
                    modifier = Modifier.size(30.dp)
                        .onPointerEvent(PointerEventType.Enter) { activeNext = true }
                        .onPointerEvent(PointerEventType.Exit) { activeNext = false }
                        .clickable {
                            if (indexSong.value == songsList.lastIndex) {
                                indexSong.value = 0
                            } else {
                                indexSong.value++
                            }
                        },
                    imageVector = Icons.Filled.SkipNext,
                    contentDescription = null,
                    tint = if (activeNext) Color.White else Color.Gray
                )
            }

            Row(
                modifier = Modifier.onPointerEvent(PointerEventType.Enter) { activeTimeMusic = true }
                    .onPointerEvent(PointerEventType.Exit) { activeTimeMusic = false }.padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                val interactionSource = MutableInteractionSource()

                AnimatedVisibility(
                    visible = activeTimeMusic,
                    enter = fadeIn(animationSpec = tween(400)),
                    exit = fadeOut(animationSpec = tween(400))
                ) {
                    DefaultText(
                        modifier = Modifier.padding(bottom = 4.dp).wrapContentSize(unbounded = true),
                        text = "${currentTime.value.toLong()/1000/60}:${if(currentTime.value.toLong()/1000 > 9) currentTime.value.toLong()/1000 else (currentTime.value.toLong()/1000).toString().padStart(2, '0')}",
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        letterSpacing = TextUnit(-0.5F, TextUnitType.Sp)
                    )
                }

                Slider(
                    modifier = Modifier.width(300.dp),
                    value = currentTime.value,
                    onValueChange = { currentTime.value = it },
                    interactionSource = interactionSource,
                    valueRange = 0F..audioPlayerController.getFullTime().toFloat(),
                    track = { sliderState ->
                        SliderDefaults.Track(
                            modifier = Modifier.scale(scaleX = 1F, scaleY = 0.9F),
                            sliderState = sliderState,
                            colors = customSliderColors(activeTrackColor = Color.White, inactiveTrackColor = Color.Gray.copy(alpha = 0.4F))
                        )
                    },
                    thumb = {
                        SliderDefaults.Thumb(
                            modifier = Modifier.clip(CircleShape).border(4.dp, Color(0xFF121212), CircleShape).padding(2.dp),
                            interactionSource = interactionSource,
                            thumbSize = DpSize(width = 15.dp, height = 15.dp),
                            colors = customSliderColors(
                                thumbColor = Color.White
                            )
                        )
                    }
                )

                AnimatedVisibility(
                    modifier = Modifier.padding(bottom = 4.dp).wrapContentSize(unbounded = true),
                    visible = activeTimeMusic,
                    enter = fadeIn(animationSpec = tween(400)),
                    exit = fadeOut(animationSpec = tween(400))
                ) {
                    DefaultText(
                        text = "${audioPlayerController.getFullTime()/1000/60}:${audioPlayerController.getFullTime()/1000/60*10}",
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        letterSpacing = TextUnit(-0.5F, TextUnitType.Sp)
                    )
                }
            }
        }

        Row(
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            var activeVolume by remember { mutableStateOf(false) }

            IconButton(onClick = {}) {
                Icon(
                    modifier = Modifier.size(30.dp)
                        .onPointerEvent(PointerEventType.Enter) { activeVolume = true }
                        .onPointerEvent(PointerEventType.Exit) { activeVolume = false },
                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                    contentDescription = null,
                    tint = if (activeVolume) Color.White else Color.Gray
                )
            }
        }
    }
}

private fun playSong(
    song: Song,
    audioPlayerController: AudioPlayerController,
    selectedSongIndex: MutableState<Int>,
    songsList: List<Song>,
    currentTime: MutableState<Float>
) {
    audioPlayerController.prepare(song.urlMusic, listener = object : AudioPlayerListener {
        override fun onReady() {

        }

        override fun timeChanged(newTime: Long) {
            currentTime.value = newTime.toFloat()
        }

        override fun onAudioFinished() {
            if (selectedSongIndex.value < songsList.lastIndex) {
                selectedSongIndex.value += 1
            }
        }

        override fun onError() {
            if (selectedSongIndex.value < songsList.lastIndex) {
                selectedSongIndex.value += 1
            }
        }
    })
}