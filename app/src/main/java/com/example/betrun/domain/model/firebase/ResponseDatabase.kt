package com.example.betrun.domain.model.firebase

sealed class ResponseDatabase<out T> {
    data object Loading: ResponseDatabase<Nothing>()

    data class Success<out T>(
        val data: T?
    ): ResponseDatabase<T>()

    data class Failure(
        val e: Exception?
    ): ResponseDatabase<Nothing>()
}