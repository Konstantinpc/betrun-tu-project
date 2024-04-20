package com.example.betrun.domain.usecase.home

import com.example.betrun.domain.repository.ProfileRepository

class SetProfilePicUseCase(
    private val repo: ProfileRepository
) {
    suspend operator fun invoke(
        uid: String,
        byteArray: ByteArray
    ) = repo.setProfilePic(uid, byteArray)
}