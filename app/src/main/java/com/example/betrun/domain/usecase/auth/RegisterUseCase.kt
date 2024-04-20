package com.example.betrun.domain.usecase.auth

import com.example.betrun.domain.repository.AuthRepository

class RegisterUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        authRepository.register(email, password)
}