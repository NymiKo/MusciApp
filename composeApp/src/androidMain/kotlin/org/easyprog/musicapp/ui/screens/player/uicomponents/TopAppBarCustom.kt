package org.easyprog.musicapp.ui.screens.player.uicomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import custom_elements.text.DefaultText
import org.easyprog.musicapp.ui.theme.PurpleLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarCustom(modifier: Modifier = Modifier, title: String = "", navigationIcon: ImageVector, backgroundColor: Color, onBack: () -> Unit) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            DefaultText(
                text = title,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            Icon(
                modifier = Modifier.padding(start = 16.dp).clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onBack() },
                imageVector = navigationIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
        },
        colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = backgroundColor)
    )
}