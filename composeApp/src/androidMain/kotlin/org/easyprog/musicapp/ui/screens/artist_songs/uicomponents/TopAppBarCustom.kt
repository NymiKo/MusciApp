package org.easyprog.musicapp.ui.screens.artist_songs.uicomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import custom_elements.text.DefaultText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarCustom(modifier: Modifier = Modifier, title: String, onBack: () -> Unit) {
    CenterAlignedTopAppBar(
        modifier = modifier.padding(horizontal = 16.dp),
        title = {
            DefaultText(
                text = title,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        navigationIcon = {
            Icon(modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onBack() },
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null
            )
        }
    )
}