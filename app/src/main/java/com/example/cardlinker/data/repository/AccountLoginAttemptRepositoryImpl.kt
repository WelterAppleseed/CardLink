package com.example.cardlinker.data.repository

import com.example.cardlinker.data.local.dao.AccountDao
import com.example.cardlinker.domain.models.Account
import com.example.cardlinker.domain.repository.AccountLoginAttemptRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountLoginAttemptRepositoryImpl @Inject constructor(private val accountDao: AccountDao): AccountLoginAttemptRepository {
    override fun loginAttempt(
        nickOrEmail: String,
        encodedPassword: String
    ): Flow<Account?> {
        return accountDao.loginAccount(nickOrEmail, encodedPassword)
    }

}