package custom_elements.slider

import androidx.compose.material3.SliderColors
import androidx.compose.ui.graphics.Color


fun customSliderColors(
    thumbColor: Color = Color.Gray,
    activeTrackColor: Color = Color.Gray,
    inactiveTrackColor: Color = Color.Gray,
    activeTickColor: Color = Color.Gray,
    disabledActiveTickColor: Color = Color.Gray,
    disabledThumbColor: Color = Color.Gray,
    disabledActiveTrackColor: Color = Color.Gray,
    disabledInactiveTickColor: Color = Color.Gray,
    disabledInactiveTrackColor: Color = Color.Gray,
    inactiveTickColor: Color = Color.Gray
): SliderColors {
    return SliderColors(
        thumbColor = thumbColor,
        activeTrackColor = activeTrackColor,
        inactiveTrackColor = inactiveTrackColor,
        activeTickColor = activeTickColor,
        disabledActiveTickColor = disabledActiveTickColor,
        disabledThumbColor = disabledThumbColor,
        disabledActiveTrackColor = disabledActiveTrackColor,
        disabledInactiveTickColor = disabledInactiveTickColor,
        disabledInactiveTrackColor = disabledInactiveTrackColor,
        inactiveTickColor = inactiveTickColor
    )
}