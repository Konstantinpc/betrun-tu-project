package com.example.betrun.domain.repository

import com.example.betrun.domain.model.firebase.ResponseDatabase
import com.example.betrun.domain.model.map.Run
import com.example.betrun.domain.model.questions.PersonData
import com.google.firebase.storage.UploadTask
import java.time.LocalDate

interface ProfileRepository {
    suspend fun getRuns(uid: String): ResponseDatabase<List<Run>>

    suspend fun getPersonData(uid: String): ResponseDatabase<PersonData>

    suspend fun getProfilePic(uid: String): ResponseDatabase<String>

    suspend fun getWeightChartData(uid: String): ResponseDatabase<Map<LocalDate, Float>>

    suspend fun setCurrentWeight(uid: String, kg: Float): ResponseDatabase<Void>

    suspend fun setProfilePic(uid: String, byteArray: ByteArray): ResponseDatabase<UploadTask.TaskSnapshot>
}