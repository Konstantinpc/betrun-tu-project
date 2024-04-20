package com.example.betrun.domain.usecase.home

import com.example.betrun.domain.repository.ProfileRepository

class GetRunsUseCase(
    private val repo: ProfileRepository
) {
    suspend operator fun invoke(
        uid: String
    ) = repo.getRuns(uid)
}