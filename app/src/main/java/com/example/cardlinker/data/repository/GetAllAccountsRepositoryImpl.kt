package com.example.cardlinker.data.repository

import com.example.cardlinker.data.local.dao.AccountDao
import com.example.cardlinker.domain.models.Account
import com.example.cardlinker.domain.repository.GetAllAccountsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllAccountsRepositoryImpl @Inject constructor(private val accountDao: AccountDao): GetAllAccountsRepository {
    override suspend fun getAllAccounts(): Flow<List<Account>> {
        return accountDao.getAll()
    }

}