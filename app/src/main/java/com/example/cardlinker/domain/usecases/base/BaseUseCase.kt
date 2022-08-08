package com.example.cardlinker.domain.usecases.base

interface BaseUseCase<T> {
    fun execute(): T
}