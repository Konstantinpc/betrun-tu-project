package com.example.betrun.data.repository

import com.example.betrun.domain.model.firebase.ResponseDatabase
import com.example.betrun.domain.model.questions.PersonData
import com.example.betrun.domain.repository.QuestionsRepository
import com.example.betrun.util.Constants
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await

class QuestionsRepositoryImpl(
    private val db: DatabaseReference
): QuestionsRepository {
    override suspend fun setPersonData(
        uid: String,
        personData: PersonData,
        unit: () -> Unit
    ): ResponseDatabase<Void> = try {
        val data = db.child(Constants.USERS_KEY).child(uid).child(Constants.PERSON_DATA_KEY)
            .setValue(personData)
            .addOnFailureListener {
                ResponseDatabase.Failure(it)
            }
            .addOnSuccessListener {
                unit()
            }.await()
        ResponseDatabase.Success(data)
    } catch (e: Exception) {
        ResponseDatabase.Failure(e)
    }

}