package com.example.betrun.domain.usecase.auth

import com.example.betrun.domain.repository.AuthRepository
import kotlinx.coroutines.flow.flow

class IsLoggedInUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke() = flow { emit(authRepository.isLoggedIn()) }
}