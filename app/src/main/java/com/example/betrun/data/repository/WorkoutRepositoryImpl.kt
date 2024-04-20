package com.example.betrun.data.repository

import com.example.betrun.domain.model.firebase.ResponseDatabase
import com.example.betrun.domain.model.firebase.ResponseDatabase.Failure
import com.example.betrun.domain.model.firebase.ResponseDatabase.Success
import com.example.betrun.domain.model.map.Run
import com.example.betrun.domain.repository.WorkoutRepository
import com.example.betrun.util.Constants
import com.example.betrun.util.Constants.RUNS_KEY
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WorkoutRepositoryImpl(
    private val db: DatabaseReference,
    private val storage: FirebaseStorage
): WorkoutRepository {

    override suspend fun setNewRun(uid: String, byteArray: ByteArray, run: Run): ResponseDatabase<UploadTask.TaskSnapshot> = try {
        val sdf = SimpleDateFormat("ddMyyyyhhmmss", Locale.US)
        val currentDate = sdf.format(Date())
        val newRunStorageRef = storage.getReferenceFromUrl(Constants.STORAGE_URL).child("$uid/$RUNS_KEY/$currentDate.jpg")
        val data = newRunStorageRef.putBytes(byteArray).addOnFailureListener {
            Failure(it)
        }.addOnSuccessListener { taskSnapshot ->
            taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                val runsRef = db.child(Constants.USERS_KEY).child(uid).child(RUNS_KEY)
                runsRef.push().key?.let {
                    runsRef.child(it).setValue(run.apply { img = uri.toString() })
                }
            }
        }.await()
        Success(data)
    } catch (e: Exception) {
        Failure(e)
    }
}