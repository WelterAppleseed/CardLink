package com.example.cardlinker.domain.usecases.base

interface BaseReturnUseCase<T> {
    fun execute(): T
}