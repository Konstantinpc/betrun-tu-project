package com.example.betrun.ui.screens.questions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.betrun.R
import com.example.betrun.domain.model.questions.AnswerData
import com.example.betrun.ui.screens.questions.question.SingleChoiceQuestion
import com.example.betrun.ui.screens.questions.question.TextQuestionsScreen
import com.example.betrun.ui.screens.questions.question.WheelPickerQuestion


@Composable
fun NameQuestion(
    modifier: Modifier = Modifier,
    onOptionSelected: (String) -> Unit,
) {
    TextQuestionsScreen(
        titleResourceId = R.string.name_questions,
        directionsResourceId = R.string.name_questions_description,
        modifier = modifier,
        onOptionSelected = onOptionSelected
    )
}

@Composable
fun GenderQuestion(
    selectedAnswer: AnswerData?,
    onOptionSelected: (AnswerData) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceQuestion(
        titleResourceId = R.string.gender_question,
        directionsResourceId = R.string.gender_question_description,
        possibleAnswers = listOf(
            AnswerData(R.string.male),
            AnswerData(R.string.women),
        ),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionSelected,
        modifier = modifier,
    )
}

@Composable
fun TrainingIntensityQuestion(
    selectedAnswer: AnswerData?,
    onOptionSelected: (AnswerData) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceQuestion(
        titleResourceId = R.string.training_intensity_question,
        directionsResourceId = R.string.training_intensity_question_description,
        possibleAnswers = listOf(
            AnswerData(R.string.light, R.string.light_sec),
            AnswerData(R.string.medium, R.string.medium_sec),
            AnswerData(R.string.intense, R.string.intense_sec),
        ),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionSelected,
        modifier = modifier,
    )
}

@Composable
fun PaymentMethodQuestion(
    selectedAnswer: AnswerData?,
    onOptionSelected: (AnswerData) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceQuestion(
        titleResourceId = R.string.payment_method_question,
        directionsResourceId = R.string.payment_method_question_description,
        possibleAnswers = listOf(
            AnswerData(R.string.google_pay),
            AnswerData(R.string.bank_card),
            AnswerData(R.string.paypal),
        ),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionSelected,
        modifier = modifier,
    )
}

@Composable
fun CurrentWeightQuestion(
    selectedAnswer: Int?,
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    WheelPickerQuestion(
        titleResourceId = R.string.current_weight_question,
        directionsResourceId = R.string.current_weight_question_description,
        initIndex = 80,
        secondary = R.string.current_weight_question_helper,
        count = 250,
        selected = selectedAnswer,
        onOptionSelected = onOptionSelected,
        modifier = modifier,
    )
}

@Composable
fun HeightQuestion(
    selectedAnswer: Int?,
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    WheelPickerQuestion(
        titleResourceId = R.string.height_question,
        directionsResourceId = R.string.height_question_description,
        initIndex = 170,
        secondary = R.string.height_question_helper,
        count = 250,
        selected = selectedAnswer,
        onOptionSelected = onOptionSelected,
        modifier = modifier,
    )
}

@Composable
fun WeightGoalQuestion(
    selectedAnswer: Int?,
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    WheelPickerQuestion(
        titleResourceId = R.string.weight_goal_question,
        directionsResourceId = R.string.weight_goal_question_description,
        initIndex = 80,
        secondary = R.string.current_weight_question_helper,
        count = 250,
        selected = selectedAnswer,
        onOptionSelected = onOptionSelected,
        modifier = modifier,
    )
}