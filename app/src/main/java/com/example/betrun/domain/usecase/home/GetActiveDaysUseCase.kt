package com.example.betrun.domain.usecase.home

import com.example.betrun.domain.repository.ProgramRepository

class GetActiveDaysUseCase(
    private val repo: ProgramRepository
) {
    suspend operator fun invoke(
        uid: String
    ) = repo.getActiveDays(uid)
}