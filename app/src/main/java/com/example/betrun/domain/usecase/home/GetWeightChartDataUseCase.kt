package com.example.betrun.domain.usecase.home

import com.example.betrun.domain.repository.ProfileRepository

class GetWeightChartDataUseCase(
    private val repo: ProfileRepository
) {
    suspend operator fun invoke(
        uid: String
    ) = repo.getWeightChartData(uid)
}