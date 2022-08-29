package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.repository.UpdateCardAccountHashcodeRepository
import com.example.cardlinker.domain.usecases.base.BaseSuspendVoidUseCase
import javax.inject.Inject

class UpdateCardAccountHashcodeUseCase @Inject constructor(private val updateCardAccountHashcodeRepository: UpdateCardAccountHashcodeRepository): BaseSuspendVoidUseCase {
    private var accountHashCode = 0
    fun saveInput(accountHashCode: Int) = run { this.accountHashCode = accountHashCode }
    override suspend fun execute() {
        updateCardAccountHashcodeRepository.updateWithAccountHashcode(accountHashCode)
    }
}