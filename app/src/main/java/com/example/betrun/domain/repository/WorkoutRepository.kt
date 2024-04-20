package com.example.betrun.domain.repository

import com.example.betrun.domain.model.firebase.ResponseDatabase
import com.example.betrun.domain.model.map.Run
import com.google.firebase.storage.UploadTask

interface WorkoutRepository {
    suspend fun setNewRun(uid: String, byteArray: ByteArray, run: Run): ResponseDatabase<UploadTask.TaskSnapshot>
}