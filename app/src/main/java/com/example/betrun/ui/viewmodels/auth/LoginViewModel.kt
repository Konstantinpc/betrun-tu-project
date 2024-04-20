package com.example.betrun.ui.viewmodels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betrun.domain.model.auth.Response
import com.example.betrun.domain.usecase.auth.LoginUseCase
import com.example.betrun.domain.usecase.auth.ResetPasswordUseCase
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {

    private val _loginFlow = MutableSharedFlow<Response<AuthResult>>()
    val loginFlow = _loginFlow

    private val _resetPasswordFlow = MutableSharedFlow<Response<Void?>>()
    val resetPasswordFlow = _resetPasswordFlow

    fun login(email: String, password: String) = viewModelScope.launch {
        loginUseCase.invoke(email, password).collect { response ->
            _loginFlow.emit(response)
        }
    }

    fun resetPassword(email: String) = viewModelScope.launch {
        resetPasswordUseCase.invoke(email).collect { response ->
            _resetPasswordFlow.emit(response)
        }
    }

}