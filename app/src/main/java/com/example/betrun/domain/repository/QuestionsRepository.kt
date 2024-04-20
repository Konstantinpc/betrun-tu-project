package com.example.betrun.domain.repository

import com.example.betrun.domain.model.firebase.ResponseDatabase
import com.example.betrun.domain.model.questions.PersonData

interface QuestionsRepository {
    suspend fun setPersonData(uid: String, personData: PersonData, unit: () -> Unit): ResponseDatabase<Void>
}