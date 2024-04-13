package screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import screens.components.PlayerComponent
import screens.custom_elements.text.DefaultText

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.95F))
    ) {
        Title(modifier = Modifier.align(Alignment.Top))
        Trends(modifier = Modifier)
    }
}

@Composable
private fun Title(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(horizontal = 40.dp, vertical = 8.dp)
    ) {
        Text(
            modifier = Modifier,
            fontWeight = FontWeight.Bold,
            text = "Не яндекс",
            fontSize = 16.sp,
            color = Color.Yellow
        )
        Text(
            modifier = Modifier,
            text = "Музыка",
            fontSize = 26.sp,
            letterSpacing = TextUnit(4F, TextUnitType.Sp),
            fontWeight = FontWeight.ExtraBold,
            color = Color.Yellow
        )
    }
}

@Composable
private fun Trends(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(12.dp).fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TrendsComponent(modifier = Modifier.weight(1F))
        PlayerComponent(modifier = Modifier)
    }
}

@Composable
private fun TrendsComponent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth()
            .border(0.5.dp, Color(0xFF1B1B1B), RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp)).background(Color(0xFF121212))
            .padding(horizontal = 24.dp)
    ) {
        MyWaveComponent(modifier = Modifier)
        RecommendedCategory(modifier = Modifier)
    }
}

@Composable
private fun MyWaveComponent(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth().height(500.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth().align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PlayingMyWave(modifier = Modifier)
            SettingsMyWave(modifier = Modifier)
        }
        Text(
            modifier = Modifier.background(Color(0xFF2B2B2B), shape = CircleShape)
                .clip(CircleShape).align(Alignment.BottomStart).padding(horizontal = 16.dp)
                .padding(top = 4.dp, bottom = 8.dp),
            text = "Для вас",
            color = Color.White,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun PlayingMyWave(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(50.dp),
            imageVector = Icons.Default.PlayArrow,
            contentDescription = null,
            tint = Color.White
        )
        Text(
            modifier = Modifier,
            text = "Моя волна",
            fontSize = 28.sp,
            color = Color.White,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
private fun SettingsMyWave(modifier: Modifier = Modifier) {
    Button(
        modifier = modifier.padding(start = 16.dp),
        colors = ButtonColors(
            containerColor = Color(0xFF1B1B1B),
            contentColor = Color.White,
            disabledContentColor = Color.Black,
            disabledContainerColor = Color.Black
        ),
        onClick = { }
    ) {
        Row(
            modifier = Modifier.padding(6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                contentDescription = null,
                imageVector = Icons.Filled.FilterList,
                tint = Color.White
            )
            Text(
                text = "Настроить",
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun RecommendedCategory(modifier: Modifier = Modifier) {
    var active by remember { mutableStateOf(false) }
    Row(
        modifier = modifier.padding(top = 26.dp).fillMaxWidth()
            .onPointerEvent(PointerEventType.Enter) { active = true }
            .onPointerEvent(PointerEventType.Exit) { active = false },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            DefaultText(
                text = "Рекомендуем новинки",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(
                modifier = Modifier.padding(
                    start = animateDpAsState(
                        targetValue = if (active) 12.dp else 4.dp,
                        animationSpec = tween(300)
                    ).value
                ).size(30.dp),
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Gray
            )
        }
        AnimatedVisibility(
            visible = active,
            enter = fadeIn(animationSpec = tween(500)),
            exit = fadeOut(animationSpec = tween(500))
        ) {
            Row {
                ChangeRecommendedSong(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft
                )
                ChangeRecommendedSong(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ChangeRecommendedSong(modifier: Modifier = Modifier, imageVector: ImageVector) {
    var active by remember { mutableStateOf(false) }
    Icon(
        modifier = modifier.padding(start = 8.dp).size(32.dp)
            .border(2.dp, Color.White.copy(alpha = if (active) 0.5F else 0.2F), CircleShape).clip(CircleShape)
            .onPointerEvent(PointerEventType.Enter) { active = true }
            .onPointerEvent(PointerEventType.Exit) { active = false }
            .padding(4.dp),
        imageVector = imageVector,
        tint = Color.White,
        contentDescription = null
    )
}