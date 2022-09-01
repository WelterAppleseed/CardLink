package com.example.cardlinker.data.repository

import com.example.cardlinker.data.local.dao.AccountDao
import com.example.cardlinker.domain.models.Account
import com.example.cardlinker.domain.repository.InsertAccountRepository
import javax.inject.Inject

class InsertAccountRepositoryImpl @Inject constructor(private val accountDao: AccountDao): InsertAccountRepository {
    override suspend fun insertAccount(account: Account) {
        accountDao.setAccount(account)
    }
}