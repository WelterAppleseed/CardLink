package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.repository.DeleteAccountRepository
import com.example.cardlinker.domain.usecases.base.BaseSuspendVoidUseCase
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(private val deleteAccountRepository: DeleteAccountRepository): BaseSuspendVoidUseCase {

    private var email: String? = null

    fun saveInput(email: String) = run { this.email = email }

    override suspend fun execute() {
            deleteAccountRepository.deleteAccount(email!!)
    }

}