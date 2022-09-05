package com.example.cardlinker.data.repository

import com.example.cardlinker.data.local.dao.AccountDao
import com.example.cardlinker.domain.models.Account
import com.example.cardlinker.domain.repository.DeleteAccountRepository
import javax.inject.Inject

class DeleteAccountRepositoryImpl @Inject constructor(private val accountDao: AccountDao): DeleteAccountRepository {
    override suspend fun deleteAccount(email: String) {
        accountDao.deleteAccount(email)
    }

}