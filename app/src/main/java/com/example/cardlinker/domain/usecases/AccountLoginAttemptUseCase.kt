package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.models.Account
import com.example.cardlinker.domain.repository.AccountLoginAttemptRepository
import com.example.cardlinker.domain.usecases.base.BaseReturnUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountLoginAttemptUseCase @Inject constructor(private val accountLoginAttemptRepository: AccountLoginAttemptRepository): BaseReturnUseCase<Flow<Account?>> {
    private var nickOrEmail = ""
    private var encodedPassword = ""
    fun saveInput(nickOrEmail: String, encodedPassword: String) = run {
        this.nickOrEmail = nickOrEmail
        this.encodedPassword = encodedPassword
    }
    override fun execute(): Flow<Account?> {
        return accountLoginAttemptRepository.loginAttempt(nickOrEmail, encodedPassword)
    }

}