package com.example.betrun.ui.screens.questions.question

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun TextQuestionsScreen(
    @StringRes titleResourceId: Int,
    @StringRes directionsResourceId: Int,
    modifier: Modifier = Modifier,
    onOptionSelected: (String) -> Unit,
) {
    val name = remember {
        mutableStateOf("")
    }

    QuestionHolder(
        titleResourceId = titleResourceId,
        descriptionResourceId = directionsResourceId,
        modifier = modifier.selectableGroup(),
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            value = name.value,
            onValueChange = { text -> name.value = text; onOptionSelected(text) },
            label = { Text("Name") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
    }
}