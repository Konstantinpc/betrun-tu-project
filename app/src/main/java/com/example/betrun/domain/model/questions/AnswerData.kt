package com.example.betrun.domain.model.questions

import androidx.annotation.StringRes

data class AnswerData(
    @StringRes val stringResourceId: Int,
    @StringRes val secondaryResId: Int = 0,
)