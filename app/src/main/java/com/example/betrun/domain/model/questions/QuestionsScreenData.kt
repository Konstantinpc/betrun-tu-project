package com.example.betrun.domain.model.questions

data class QuestionsScreenData(
    val questionIndex: Int,
    val questionCount: Int,
    val shouldShowPreviousButton: Boolean,
    val shouldShowDoneButton: Boolean,
    val betrunQuestions: BetrunQuestions,
)