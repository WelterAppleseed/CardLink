package com.example.cardlinker.data.repository

import com.example.cardlinker.data.local.dao.AccountDao
import com.example.cardlinker.domain.models.Account
import com.example.cardlinker.domain.repository.UpdateAccountDataRepository
import javax.inject.Inject

class UpdateAccountDataRepositoryImpl @Inject constructor(private val accountDao: AccountDao): UpdateAccountDataRepository {
    override suspend fun updateData(account: Account) {
        accountDao.updateAccountData(account.id, account.nickname, account.email)
    }

}