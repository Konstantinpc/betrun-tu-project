package com.example.betrun.ui.screens.questions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.betrun.domain.model.questions.QuestionsScreenData
import com.example.betrun.ui.components.questions.QuestionsBottomBar
import com.example.betrun.ui.components.questions.QuestionsTopAppBar

@Composable
fun QuestionsScreen(
    surveyScreenData: QuestionsScreenData,
    isNextEnabled: Boolean,
    onClosePressed: () -> Unit,
    onPreviousPressed: () -> Unit,
    onNextPressed: () -> Unit,
    onDonePressed: () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface),
        topBar = {
            QuestionsTopAppBar(
                questionIndex = surveyScreenData.questionIndex,
                totalQuestionsCount = surveyScreenData.questionCount,
                onClosePressed = onClosePressed,
            )
        },
        content = content,
        bottomBar = {
            QuestionsBottomBar(
                shouldShowSecondaryButton = surveyScreenData.shouldShowPreviousButton,
                shouldShowContinueButton = surveyScreenData.shouldShowDoneButton,
                isNextButtonEnabled = isNextEnabled,
                onPreviousPressed = onPreviousPressed,
                onNextPressed = onNextPressed,
                onContinuePressed = onDonePressed
            )
        }
    )
}