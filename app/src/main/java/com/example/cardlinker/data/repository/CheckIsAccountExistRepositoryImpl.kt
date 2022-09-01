package com.example.cardlinker.data.repository

import com.example.cardlinker.data.local.dao.AccountDao
import com.example.cardlinker.domain.repository.CheckIsAccountExistRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckIsAccountExistRepositoryImpl @Inject constructor(private val accountDao: AccountDao): CheckIsAccountExistRepository {
    override fun checkIsExist(email: String): Flow<Boolean> {
        return accountDao.isAccountExisted(email)
    }

}