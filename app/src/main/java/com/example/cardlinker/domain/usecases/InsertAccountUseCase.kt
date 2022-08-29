package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.models.Account
import com.example.cardlinker.domain.repository.InsertAccountRepository
import com.example.cardlinker.domain.usecases.base.BaseSuspendVoidUseCase
import javax.inject.Inject

class InsertAccountUseCase @Inject constructor(
    private val insertAccountRepository: InsertAccountRepository) :
    BaseSuspendVoidUseCase {
    private var account: Account? = null
    fun saveInput(account: Account) = run { this.account = account }
    override suspend fun execute() {
        insertAccountRepository.insertAccount(account!!)
    }
}