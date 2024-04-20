package com.example.betrun.ui.viewmodels.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betrun.domain.model.firebase.ResponseDatabase
import com.example.betrun.domain.model.firebase.ResponseDatabase.Loading
import com.example.betrun.domain.model.map.Run
import com.example.betrun.domain.model.questions.PersonData
import com.example.betrun.domain.usecase.auth.GetUserUidUseCase
import com.example.betrun.domain.usecase.auth.LogoutUseCase
import com.example.betrun.domain.usecase.home.GetActiveDaysUseCase
import com.example.betrun.domain.usecase.home.GetPersonDataUseCase
import com.example.betrun.domain.usecase.home.GetProfilePicUseCase
import com.example.betrun.domain.usecase.home.GetRunsUseCase
import com.example.betrun.domain.usecase.home.GetWeightChartDataUseCase
import com.example.betrun.domain.usecase.home.SetCurrentWeightUseCase
import com.example.betrun.domain.usecase.home.SetNewRunUseCase
import com.example.betrun.domain.usecase.home.SetPersonDataUseCase
import com.example.betrun.domain.usecase.home.SetProfilePicUseCase
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class HomeViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val getUserUidUseCase: GetUserUidUseCase,
    private val getRunsUseCase: GetRunsUseCase,
    private val getPersonDataUseCase: GetPersonDataUseCase,
    private val getProfilePicUseCase: GetProfilePicUseCase,
    private val getWeightChartDataUseCase: GetWeightChartDataUseCase,
    private val setCurrentWeightUseCase: SetCurrentWeightUseCase,
    private val setProfilePicUseCase: SetProfilePicUseCase,
    private val getActiveDaysUseCase: GetActiveDaysUseCase,
    private val setNewRunUseCase: SetNewRunUseCase,
    private val setPersonDataUseCase: SetPersonDataUseCase
) : ViewModel() {

    private var userUidState = mutableStateOf("")

    private val _dialogRun = MutableStateFlow<Run?>(null)
    val dialogRun = _dialogRun.asStateFlow()

    var runsResponse by mutableStateOf<ResponseDatabase<List<Run>>>(Loading)
        private set

    var personDataResponse by mutableStateOf<ResponseDatabase<PersonData>>(Loading)
        private set

    var profilePicResponse by mutableStateOf<ResponseDatabase<String>>(Loading)
        private set

    var weightChartDataResponse by mutableStateOf<ResponseDatabase<Map<LocalDate, Float>>>(Loading)
        private set

    private var setCurrentWeightResponse by mutableStateOf<ResponseDatabase<Void>>(Loading)

    private var setProfilePicResponse by mutableStateOf<ResponseDatabase<UploadTask.TaskSnapshot>>(Loading)

    var activeDaysResponse by mutableStateOf<ResponseDatabase<List<String>>>(Loading)
        private set

    private var setNewRunResponse by mutableStateOf<ResponseDatabase<UploadTask.TaskSnapshot>>(Loading)

    private var setPersonDataResponse by mutableStateOf<ResponseDatabase<Void>>(Loading)

    init {
        getUid()
        getWeightChartData()
        getProfilePic()
        getRuns()
        getPersonData()
        getActiveDays()
    }

    fun logout() = viewModelScope.launch {
        logoutUseCase.invoke()
    }

    private fun getUid() = viewModelScope.launch {
        getUserUidUseCase.invoke().collect {
            userUidState.value = it
        }
    }

    fun setDialogRun(run: Run?) {
        _dialogRun.value = run
    }

    private fun getRuns() = viewModelScope.launch {
        runsResponse = Loading
        runsResponse = getRunsUseCase(userUidState.value)
    }

    private fun getPersonData() = viewModelScope.launch {
        personDataResponse = Loading
        personDataResponse = getPersonDataUseCase(userUidState.value)
    }

    private fun getProfilePic() = viewModelScope.launch {
        profilePicResponse = Loading
        profilePicResponse = getProfilePicUseCase(userUidState.value)
    }

    private fun getWeightChartData() = viewModelScope.launch {
        weightChartDataResponse = Loading
        weightChartDataResponse = getWeightChartDataUseCase(userUidState.value)
    }

    fun setCurrentWeight(kg: Float) = viewModelScope.launch {
        setCurrentWeightResponse = Loading
        setCurrentWeightResponse = setCurrentWeightUseCase(userUidState.value, kg)
        getWeightChartData()
    }

    fun setProfilePic(byteArray: ByteArray) = viewModelScope.launch {
        setProfilePicResponse = Loading
        setProfilePicResponse = setProfilePicUseCase(userUidState.value, byteArray)
        getProfilePic()
    }

    private fun getActiveDays() = viewModelScope.launch {
        activeDaysResponse = Loading
        activeDaysResponse = getActiveDaysUseCase(userUidState.value)
    }

    fun setNewRun(run: Run, byteArray: ByteArray) = viewModelScope.launch {
        setNewRunResponse = Loading
        setNewRunResponse = setNewRunUseCase(userUidState.value, byteArray, run)
    }

    fun setPersonData(personData: PersonData, unit: () -> Unit) = viewModelScope.launch {
        setPersonDataResponse = Loading
        setPersonDataResponse = setPersonDataUseCase(userUidState.value, personData, unit)
    }
}