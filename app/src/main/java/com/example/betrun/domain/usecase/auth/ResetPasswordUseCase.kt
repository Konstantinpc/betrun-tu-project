package com.example.betrun.domain.usecase.auth

import com.example.betrun.domain.repository.AuthRepository

class ResetPasswordUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String) = authRepository.resetPassword(email)
}