package com.example.cardlinker.data.repository

import com.example.cardlinker.data.local.dao.AccountDao
import com.example.cardlinker.domain.models.Account
import com.example.cardlinker.domain.repository.UpdateAccountDataRepository
import javax.inject.Inject

class UpdateAccountDataRepositoryImpl @Inject constructor(private val accountDao: AccountDao): UpdateAccountDataRepository {
    override suspend fun updateData(data: Pair<Account, String>) {
        accountDao.updateAccount(data.first.nickname, data.first.email, data.first.encodedPassword, data.second)
    }

}