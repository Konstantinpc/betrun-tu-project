package com.example.betrun.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.betrun.domain.model.firebase.ResponseDatabase
import com.example.betrun.domain.model.firebase.ResponseDatabase.Failure
import com.example.betrun.domain.model.firebase.ResponseDatabase.Success
import com.example.betrun.domain.model.map.Run
import com.example.betrun.domain.model.questions.PersonData
import com.example.betrun.domain.repository.ProfileRepository
import com.example.betrun.util.Constants.PERSON_DATA_KEY
import com.example.betrun.util.Constants.PROFILE_PIC_KEY
import com.example.betrun.util.Constants.RUNS_KEY
import com.example.betrun.util.Constants.STORAGE_URL
import com.example.betrun.util.Constants.USERS_KEY
import com.example.betrun.util.Constants.WEIGHT_CHART_DATA_KEY
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class ProfileRepositoryImpl(
    private val db: DatabaseReference,
    private val storage: FirebaseStorage
): ProfileRepository {

    override suspend fun getRuns(uid: String): ResponseDatabase<List<Run>> = try {
        val runs = mutableListOf<Run>()
        db.child(USERS_KEY).child(uid).child(RUNS_KEY).get().await().children.forEach { snapshot ->
            snapshot.getValue(Run::class.java)?.let {
                runs.add(it)
            }
        }
        Success(runs)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun getPersonData(uid: String): ResponseDatabase<PersonData> = try {
        val personDataRef = db.child(USERS_KEY).child(uid).child(PERSON_DATA_KEY)
        val personData = personDataRef.get().await().getValue(PersonData::class.java)
        Success(personData)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun getProfilePic(uid: String): ResponseDatabase<String> = try {
        val profilePicRef = db.child(USERS_KEY).child(uid).child(PERSON_DATA_KEY).child(PROFILE_PIC_KEY)
        val profilePic = profilePicRef.get().await().getValue(String::class.java)
        Success(profilePic)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun getWeightChartData(uid: String): ResponseDatabase<Map<LocalDate, Float>> = try {
        val data = mutableMapOf<LocalDate, Float>()
        db.child(USERS_KEY).child(uid).child(WEIGHT_CHART_DATA_KEY).get()
            .await().children.forEach { snapshot ->
            snapshot.getValue(Float::class.java)?.let {
                data[LocalDate.parse(snapshot.key)] = it
            }
        }
        Success(data)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun setCurrentWeight(uid: String, kg: Float): ResponseDatabase<Void> = try {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val data = db.child(USERS_KEY).child(uid).child(WEIGHT_CHART_DATA_KEY)
            .child(sdf.format(Date()))
            .setValue(kg).await()
        Success(data)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun setProfilePic(uid: String, byteArray: ByteArray): ResponseDatabase<UploadTask.TaskSnapshot> = try {
        val sdf = SimpleDateFormat("ddMyyyyhhmmss", Locale.US)
        val currentDate = sdf.format(Date())
        val profilePicsStorageRef = storage.getReferenceFromUrl(STORAGE_URL).child("$uid/$PROFILE_PIC_KEY/$currentDate.jpg")
        val data = profilePicsStorageRef.putBytes(byteArray).addOnFailureListener {
            Failure(it)
        }.addOnSuccessListener { taskSnapshot ->
            taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                db.child(USERS_KEY).child(uid).child(PERSON_DATA_KEY).child(PROFILE_PIC_KEY)
                        .setValue(uri.toString())
            }
        }.await()
        Success(data)
    } catch (e: Exception) {
        Failure(e)
    }
}