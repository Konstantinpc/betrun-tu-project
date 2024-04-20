package com.example.betrun.domain.usecase.home

import com.example.betrun.domain.repository.ProfileRepository

class GetPersonDataUseCase(
    private val repo: ProfileRepository
) {
    suspend operator fun invoke(
        uid: String
    ) = repo.getPersonData(uid)
}