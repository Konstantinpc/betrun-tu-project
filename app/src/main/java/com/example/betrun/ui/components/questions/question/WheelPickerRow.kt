package com.example.betrun.ui.components.questions.question

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sd.lib.compose.wheel_picker.FVerticalWheelPicker
import com.sd.lib.compose.wheel_picker.FWheelPickerFocusVertical
import com.sd.lib.compose.wheel_picker.rememberFWheelPickerState
import kotlin.math.abs

@Composable
fun WheelPickerRow(
    modifier: Modifier = Modifier,
    initIndex: Int = 80,
    secondary: String = "kg",
    count: Int = 250,
    onOptionSelected: (Int) -> Unit = {},
){
    val state = rememberFWheelPickerState(initIndex)

    LaunchedEffect(state) {
        snapshotFlow { state.currentIndex }
            .collect {
                onOptionSelected(it)
            }
    }

    LaunchedEffect(state) {
        snapshotFlow { state.currentIndexSnapshot }
            .collect {
                onOptionSelected(it)
            }
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        FVerticalWheelPicker(
            modifier = Modifier.width(62.dp),
            itemHeight = 44.dp,
            unfocusedCount = 2,
            focus = {
                FWheelPickerFocusVertical(dividerColor = Color.Transparent, dividerSize = 0.dp)
            },
            state = state,
            count = count,
        ) { index ->
            Text(
                text = index.toString(),
                style = if (index == state.currentIndex) {
                    MaterialTheme.typography.displayLarge
                } else MaterialTheme.typography.displayMedium,
                color = if (index == state.currentIndex) {
                    MaterialTheme.colorScheme.onSurface
                } else if (abs(index - state.currentIndex) == 1) {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                }
            )
        }
        Spacer(modifier = Modifier.size(6.dp))
        Text(
            modifier = Modifier.padding(top = 5.dp),
            text = secondary,
            style = MaterialTheme.typography.titleMedium
        )
    }
}