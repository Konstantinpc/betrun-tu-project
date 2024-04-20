package com.example.betrun.data.repository

import com.example.betrun.domain.model.firebase.ResponseDatabase
import com.example.betrun.domain.repository.ProgramRepository
import com.example.betrun.util.Constants
import com.example.betrun.util.Constants.DATE_KEY
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await

class ProgramRepositoryImpl(
    private val db: DatabaseReference
): ProgramRepository {

    override suspend fun getActiveDays(uid: String): ResponseDatabase<List<String>> = try {
        val activeDays = mutableListOf<String>()
        db.child(Constants.USERS_KEY).child(uid).child(Constants.RUNS_KEY).get()
            .await().children.forEach { snapshot ->
                snapshot.child(DATE_KEY).getValue(String::class.java)?.let {
                    activeDays.add(it)
                }
            }
        ResponseDatabase.Success(activeDays)
    } catch (e: Exception) {
        ResponseDatabase.Failure(e)
    }
}