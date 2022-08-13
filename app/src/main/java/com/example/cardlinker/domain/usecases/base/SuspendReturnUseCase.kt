package com.example.cardlinker.domain.usecases.base

interface SuspendReturnUseCase<T> {
    suspend fun execute(): T

}