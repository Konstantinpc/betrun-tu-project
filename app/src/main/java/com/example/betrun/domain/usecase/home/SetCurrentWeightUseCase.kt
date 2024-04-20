package com.example.betrun.domain.usecase.home

import com.example.betrun.domain.repository.ProfileRepository

class SetCurrentWeightUseCase(
    private val repo: ProfileRepository
) {
    suspend operator fun invoke(
        uid: String,
        kg: Float
    ) = repo.setCurrentWeight(uid, kg)
}