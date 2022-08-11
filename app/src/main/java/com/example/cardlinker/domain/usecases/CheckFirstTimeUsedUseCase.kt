package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.repository.FirstTimeUsedManager
import com.example.cardlinker.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class CheckFirstTimeUsedUseCase @Inject constructor(private val firstTimeUsedManager: FirstTimeUsedManager): BaseUseCase<Boolean> {

    fun saveInput(isFirstTimeUsed: Boolean) = firstTimeUsedManager.isFirstTimeUsed(isFirstTimeUsed)
    override suspend fun execute(): Boolean {
        return firstTimeUsedManager.checkFirstTimeUsed()
    }

}