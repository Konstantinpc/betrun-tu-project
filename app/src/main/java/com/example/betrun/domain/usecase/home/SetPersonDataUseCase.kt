package com.example.betrun.domain.usecase.home

import com.example.betrun.domain.model.questions.PersonData
import com.example.betrun.domain.repository.QuestionsRepository

class SetPersonDataUseCase(
    private val repo: QuestionsRepository
) {
    suspend operator fun invoke(
        uid: String,
        personData: PersonData,
        unit: () -> Unit
    ) = repo.setPersonData(uid, personData, unit)
}