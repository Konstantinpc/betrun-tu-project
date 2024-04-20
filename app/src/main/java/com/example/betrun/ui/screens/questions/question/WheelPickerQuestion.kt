package com.example.betrun.ui.screens.questions.question

import androidx.annotation.StringRes
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.betrun.ui.components.questions.question.WheelPickerRow

@Composable
fun WheelPickerQuestion(
    @StringRes titleResourceId: Int,
    @StringRes directionsResourceId: Int,
    @StringRes secondary: Int,
    initIndex: Int,
    count: Int,
    selected: Int?,
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    QuestionHolder(
        titleResourceId = titleResourceId,
        descriptionResourceId = directionsResourceId,
        modifier = modifier.selectableGroup(),
    ) {
        WheelPickerRow(
            modifier = Modifier,
            initIndex = selected ?: initIndex,
            secondary = stringResource(id = secondary),
            count = count,
            onOptionSelected = onOptionSelected
        )
    }
}