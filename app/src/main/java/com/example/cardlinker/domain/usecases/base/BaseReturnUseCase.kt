package com.example.cardlinker.domain.usecases.base

interface BaseReturnUseCase<T> {
    suspend fun execute(): T
}