package com.example.cardlinker.domain.usecases.base

interface BaseUseCase<T> {
    suspend fun execute(): T
}