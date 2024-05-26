package org.easyprog.musicapp.ui.screens.player.uicomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.easyprog.musicapp.ui.theme.PurpleLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarPlayer(modifier: Modifier = Modifier, onBack: () -> Unit) {
    TopAppBar(
        modifier = modifier.clickable { onBack() },
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