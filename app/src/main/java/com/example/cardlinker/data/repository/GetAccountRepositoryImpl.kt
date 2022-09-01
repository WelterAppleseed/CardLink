package com.example.cardlinker.data.repository

import com.example.cardlinker.data.local.dao.AccountDao
import com.example.cardlinker.domain.models.Account
import com.example.cardlinker.domain.repository.GetAccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccountRepositoryImpl @Inject constructor(private val accountDao: AccountDao): GetAccountRepository {

    override fun getAccount(email: String): Flow<Account> {
        return accountDao.getAccount(email)
    }

}