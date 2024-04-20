package com.example.betrun.domain.usecase.auth

import com.example.betrun.domain.repository.AuthRepository
import kotlinx.coroutines.flow.flow

class GetUserUidUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() = flow { emit(authRepository.userUid()) }
}