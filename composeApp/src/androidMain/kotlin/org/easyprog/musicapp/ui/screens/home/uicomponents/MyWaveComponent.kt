package org.easyprog.musicapp.ui.screens.home.uicomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.easyprog.musicapp.ui.theme.PurpleDark
import org.easyprog.musicapp.ui.theme.PurpleLight

@Composable
fun MyWaveComponent(modifier: Modifier = Modifier, myWaveMode: Boolean, getSongsListMyWave: () -> Unit) {
    Box(
        modifier = Modifier.padding(16.dp).fillMaxWidth().height(200.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Brush.verticalGradient(listOf(PurpleLight, PurpleDark))),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.clickable {
                getSongsListMyWave()
            },
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                modifier = Modifier.size(50.dp),
                imageVector = if (myWaveMode) Icons.Default.Pause else Icons.Default.PlayArrow,
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
}