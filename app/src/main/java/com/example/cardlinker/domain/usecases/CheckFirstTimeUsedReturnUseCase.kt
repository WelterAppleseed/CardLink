package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.repository.FirstTimeUsedManager
import com.example.cardlinker.domain.usecases.base.BaseReturnUseCase
import javax.inject.Inject

class CheckFirstTimeUsedReturnUseCase @Inject constructor(private val firstTimeUsedManager: FirstTimeUsedManager): BaseReturnUseCase<Boolean> {

    fun saveInput(isFirstTimeUsed: Boolean) = firstTimeUsedManager.isFirstTimeUsed(isFirstTimeUsed)
    override suspend fun execute(): Boolean {
        return firstTimeUsedManager.checkFirstTimeUsed()
    }

}