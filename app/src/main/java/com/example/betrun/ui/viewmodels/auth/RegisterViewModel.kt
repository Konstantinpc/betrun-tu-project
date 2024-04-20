package com.example.betrun.ui.viewmodels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betrun.domain.model.auth.Response
import com.example.betrun.domain.usecase.auth.RegisterUseCase
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch


class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private var _registerFlow = MutableSharedFlow<Response<AuthResult>>()
    val registerFlow = _registerFlow

    fun register(email: String, password: String) = viewModelScope.launch {
        registerUseCase.invoke(email, password).collect {
            _registerFlow.emit(it)
        }
    }

}