package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.models.Account
import com.example.cardlinker.domain.repository.UpdateAccountDataRepository
import com.example.cardlinker.domain.usecases.base.BaseSuspendVoidUseCase
import javax.inject.Inject

class UpdateAccountDataUseCase @Inject constructor(private val updateAccountDataRepository: UpdateAccountDataRepository): BaseSuspendVoidUseCase {
    private var account: Account? = null

    fun saveInput(account: Account) = run { this.account = account }
    override suspend fun execute() {
        updateAccountDataRepository.updateData(account!!)
    }

}