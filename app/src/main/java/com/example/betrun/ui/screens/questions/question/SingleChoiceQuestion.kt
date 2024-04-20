package com.example.betrun.ui.screens.questions.question

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.betrun.R
import com.example.betrun.domain.model.questions.AnswerData
import com.example.betrun.ui.components.questions.question.SingleChoiceRow

@Composable
fun SingleChoiceQuestion(
    @StringRes titleResourceId: Int,
    @StringRes directionsResourceId: Int,
    possibleAnswers: List<AnswerData>,
    selectedAnswer: AnswerData?,
    onOptionSelected: (AnswerData) -> Unit,
    modifier: Modifier = Modifier,
) {
    QuestionHolder(
        titleResourceId = titleResourceId,
        descriptionResourceId = directionsResourceId,
        modifier = modifier.selectableGroup(),
    ) {
        possibleAnswers.forEach {
            val selected = it == selectedAnswer
            Column {
                SingleChoiceRow(
                    modifier = Modifier,
                    text = stringResource(id = it.stringResourceId),
                    secondary = if (it.secondaryResId != 0) stringResource(id = it.secondaryResId) else "",
                    selected = selected,
                    onOptionSelected = { onOptionSelected(it) }
                )
                Spacer(modifier = Modifier.size(19.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SingleChoiceQuestionPreview() {
    val possibleAnswers = listOf(
        AnswerData(R.string.male),
        AnswerData(R.string.women),
    )
    var selectedAnswer by remember { mutableStateOf<AnswerData?>(null) }

    SingleChoiceQuestion(
        titleResourceId = R.string.gender_question,
        directionsResourceId = R.string.gender_question_description,
        possibleAnswers = possibleAnswers,
        selectedAnswer = selectedAnswer,
        onOptionSelected = { selectedAnswer = it },
    )
}