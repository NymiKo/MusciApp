package custom_elements.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp

@Composable
fun TimeText(
    modifier: Modifier = Modifier,
    text: String
) {
    DefaultText(
        modifier = modifier,
        text = text,
        letterSpacing = TextUnit(-0.5F, TextUnitType.Sp),
        fontSize = 16.sp
    )
}