package com.example.betrun.domain.repository

import com.example.betrun.domain.model.firebase.ResponseDatabase

interface ProgramRepository {
    suspend fun getActiveDays(uid: String): ResponseDatabase<List<String>>
}