package com.example.cardlinker.domain.usecases.base

import io.reactivex.rxjava3.core.Single


interface SingleUseCase<T> {

    fun execute(): Single<T>?

}