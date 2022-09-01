package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.repository.UpdateCardAccountHashcodeRepository
import com.example.cardlinker.domain.usecases.base.BaseSuspendVoidUseCase
import javax.inject.Inject

class UpdateCardAccountHashcodeUseCase @Inject constructor(private val updateCardAccountHashcodeRepository: UpdateCardAccountHashcodeRepository): BaseSuspendVoidUseCase {
    private var newAccountHashCode = 0
    private var oldAccountHashCode = 0
    fun saveInput(newAccountHashCode: Int, oldAccountHashCode: Int) = run {
        this.newAccountHashCode = newAccountHashCode
        this.oldAccountHashCode = oldAccountHashCode
    }
    override suspend fun execute() {
        updateCardAccountHashcodeRepository.updateWithAccountHashcode(newAccountHashCode, oldAccountHashCode)
    }
}