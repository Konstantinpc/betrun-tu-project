package com.example.betrun.ui.screens.questions

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import com.example.betrun.domain.model.questions.BetrunQuestions
import com.example.betrun.ui.viewmodels.questions.QuestionsViewModel

private const val CONTENT_ANIMATION_DURATION = 350

@Composable
fun QuestionsNavScreen(
    onQuestionsComplete: () -> Unit,
    onNavUp: () -> Unit,
    viewModel: QuestionsViewModel
) {

    val surveyScreenData = viewModel.surveyScreenData

    BackHandler {
        if (!viewModel.onBackPressed()) {
            onNavUp()
        }
    }

    QuestionsScreen(
        surveyScreenData = surveyScreenData,
        isNextEnabled = viewModel.isNextEnabled,
        onClosePressed = {
            onNavUp()
        },
        onPreviousPressed = { viewModel.onPreviousPressed() },
        onNextPressed = { viewModel.onNextPressed() },
        onDonePressed = { viewModel.onDonePressed(onQuestionsComplete) }
    ) { paddingValues ->

        val modifier = Modifier
            .padding(paddingValues)
            .background(Color.White)

        AnimatedContent(
            targetState = surveyScreenData,
            transitionSpec = {
                val animationSpec: TweenSpec<IntOffset> = tween(CONTENT_ANIMATION_DURATION)

                val direction = getTransitionDirection(
                    initialIndex = initialState.questionIndex,
                    targetIndex = targetState.questionIndex,
                )

                slideIntoContainer(
                    towards = direction,
                    animationSpec = animationSpec,
                ) togetherWith slideOutOfContainer(
                    towards = direction,
                    animationSpec = animationSpec
                )
            },
            label = "surveyScreenDataAnimation"
        ) { targetState ->

            when (targetState.betrunQuestions) {

                BetrunQuestions.NAME_QUESTION -> NameQuestion(
                    modifier = modifier,
                    onOptionSelected = viewModel::onNameResponse,
                )

                BetrunQuestions.GENDER -> GenderQuestion(
                    selectedAnswer = viewModel.genderResponse,
                    onOptionSelected = viewModel::onGenderResponse,
                    modifier = modifier,
                )

                BetrunQuestions.TRAINING_INTENSITY -> TrainingIntensityQuestion(
                    selectedAnswer = viewModel.trainingIntensityResponse,
                    onOptionSelected = viewModel::onTrainingIntensityResponse,
                    modifier = modifier,
                )

                BetrunQuestions.PAYMENT_METHOD -> PaymentMethodQuestion(
                    selectedAnswer = viewModel.paymentMethodResponse,
                    onOptionSelected = viewModel::onPaymentMethodResponse,
                    modifier = modifier,
                )

                BetrunQuestions.CURRENT_WEIGHT ->
                    CurrentWeightQuestion(
                        selectedAnswer = viewModel.currentWeightResponse,
                        onOptionSelected = viewModel::onCurrentWeightResponse,
                        modifier = modifier,
                    )

                BetrunQuestions.WEIGHT_GOAL ->
                    WeightGoalQuestion(
                        selectedAnswer = viewModel.weightGoalResponse,
                        onOptionSelected = viewModel::onWeightGoalResponse,
                        modifier = modifier,
                    )

                BetrunQuestions.HEIGHT ->
                    HeightQuestion(
                        selectedAnswer = viewModel.heightResponse,
                        onOptionSelected = viewModel::onHeightResponse,
                        modifier = modifier,
                    )
            }
        }
    }
}

private fun getTransitionDirection(
    initialIndex: Int,
    targetIndex: Int
): AnimatedContentTransitionScope.SlideDirection {
    return if (targetIndex > initialIndex) {
        AnimatedContentTransitionScope.SlideDirection.Left
    } else {
        AnimatedContentTransitionScope.SlideDirection.Right
    }
}