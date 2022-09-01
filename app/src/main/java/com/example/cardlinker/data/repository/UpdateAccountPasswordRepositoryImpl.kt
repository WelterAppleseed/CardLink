package com.example.cardlinker.data.repository

import com.example.cardlinker.data.local.dao.AccountDao
import com.example.cardlinker.domain.models.Account
import com.example.cardlinker.domain.repository.UpdateAccountPasswordRepository
import javax.inject.Inject

class UpdateAccountPasswordRepositoryImpl @Inject constructor(private val accountDao: AccountDao): UpdateAccountPasswordRepository {
    override suspend fun updateData(data: Pair<Account, String>) {
        accountDao.updateAccountPassword(data.first.email, data.second)
    }

}