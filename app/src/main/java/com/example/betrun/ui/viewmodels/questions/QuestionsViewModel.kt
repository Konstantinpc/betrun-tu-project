package com.example.betrun.ui.viewmodels.questions

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.betrun.domain.model.questions.AnswerData
import com.example.betrun.domain.model.questions.BetrunQuestions
import com.example.betrun.domain.model.questions.QuestionsScreenData

class QuestionsViewModel : ViewModel() {

    private val questionOrder: List<BetrunQuestions> = listOf(
        BetrunQuestions.NAME_QUESTION,
        BetrunQuestions.GENDER,
        BetrunQuestions.HEIGHT,
        BetrunQuestions.CURRENT_WEIGHT,
        BetrunQuestions.WEIGHT_GOAL
    )

    private var questionIndex = 0

    private val _nameResponse = mutableStateOf<String?>(null)
    val nameResponse: String?
        get() = _nameResponse.value

    private val _genderResponse = mutableStateOf<AnswerData?>(null)
    val genderResponse: AnswerData?
        get() = _genderResponse.value

    private val _trainingIntensityResponse = mutableStateOf<AnswerData?>(null)
    val trainingIntensityResponse: AnswerData?
        get() = _trainingIntensityResponse.value

    private val _paymentMethodResponse = mutableStateOf<AnswerData?>(null)
    val paymentMethodResponse: AnswerData?
        get() = _paymentMethodResponse.value

    private val _currentWeightResponse = mutableStateOf<Int?>(null)
    val currentWeightResponse: Int?
        get() = _currentWeightResponse.value

    private val _weightGoalResponse = mutableStateOf<Int?>(null)
    val weightGoalResponse: Int?
        get() = _weightGoalResponse.value

    private val _heightResponse = mutableStateOf<Int?>(null)
    val heightResponse: Int?
        get() = _heightResponse.value

    private val _surveyScreenData = mutableStateOf(createSurveyScreenData())
    val surveyScreenData: QuestionsScreenData
        get() = _surveyScreenData.value

    private val _isNextEnabled = mutableStateOf(true)
    val isNextEnabled: Boolean
        get() = _isNextEnabled.value

    fun onBackPressed(): Boolean {
        if (questionIndex == 0) {
            return false
        }
        changeQuestion(questionIndex - 1)
        return true
    }

    fun onPreviousPressed() {
        if (questionIndex == 0) {
            throw IllegalStateException("onPreviousPressed when on question 0")
        }
        changeQuestion(questionIndex - 1)
    }

    fun onNextPressed() {
        changeQuestion(questionIndex + 1)
    }

    private fun changeQuestion(newQuestionIndex: Int) {
        questionIndex = newQuestionIndex
        _isNextEnabled.value = getIsNextEnabled()
        _surveyScreenData.value = createSurveyScreenData()
    }

    fun onDonePressed(onQuestionsComplete: () -> Unit) {
        onQuestionsComplete()
    }

    fun onNameResponse(text: String) {
        _nameResponse.value = text
        if (text.isBlank()) {
            _isNextEnabled.value = getIsNextEnabled()
        }
    }

    fun onGenderResponse(answerData: AnswerData) {
        _genderResponse.value = answerData
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onTrainingIntensityResponse(answerData: AnswerData) {
        _trainingIntensityResponse.value = answerData
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onPaymentMethodResponse(answerData: AnswerData) {
        _paymentMethodResponse.value = answerData
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onCurrentWeightResponse(answer: Int) {
        _currentWeightResponse.value = answer
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onWeightGoalResponse(answer: Int) {
        _weightGoalResponse.value = answer
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onHeightResponse(answer: Int) {
        _heightResponse.value = answer
        _isNextEnabled.value = getIsNextEnabled()
    }

    private fun getIsNextEnabled(): Boolean {
        return when (questionOrder[questionIndex]) {
            BetrunQuestions.NAME_QUESTION -> true
            BetrunQuestions.GENDER -> _genderResponse.value != null
            BetrunQuestions.TRAINING_INTENSITY -> _trainingIntensityResponse.value != null
            BetrunQuestions.PAYMENT_METHOD -> _paymentMethodResponse.value != null
            BetrunQuestions.CURRENT_WEIGHT -> _currentWeightResponse.value != null
            BetrunQuestions.WEIGHT_GOAL -> _weightGoalResponse.value != null
            BetrunQuestions.HEIGHT -> _heightResponse.value != null
        }
    }

    private fun createSurveyScreenData(): QuestionsScreenData {
        return QuestionsScreenData(
            questionIndex = questionIndex,
            questionCount = questionOrder.size,
            shouldShowPreviousButton = questionIndex > 0,
            shouldShowDoneButton = questionIndex == questionOrder.size - 1,
            betrunQuestions = questionOrder[questionIndex],
        )
    }
}

class QuestionsViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionsViewModel::class.java)) {
            return QuestionsViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}