package com.example.betrun.domain.usecase.home

import com.example.betrun.domain.model.map.Run
import com.example.betrun.domain.repository.WorkoutRepository

class SetNewRunUseCase(
    private val repo: WorkoutRepository
) {
    suspend operator fun invoke(
        uid: String,
        byteArray: ByteArray,
        run: Run
    ) = repo.setNewRun(uid, byteArray, run)
}