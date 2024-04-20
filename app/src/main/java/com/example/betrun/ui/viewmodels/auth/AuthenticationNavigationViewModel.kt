package com.example.betrun.ui.viewmodels.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betrun.domain.usecase.auth.IsLoggedInUseCase
import kotlinx.coroutines.launch

class AuthenticationNavigationViewModel(
    private val isLoggedInUseCase: IsLoggedInUseCase
) : ViewModel() {

    private var _isLoggedInState = mutableStateOf(false)
    val isLoggedInState = _isLoggedInState

    init {
        isLoggedIn()
    }

    private fun isLoggedIn() = viewModelScope.launch {
        isLoggedInUseCase.invoke().collect {
            _isLoggedInState.value = it
        }
    }

}