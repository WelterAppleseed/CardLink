package com.example.cardlinker.domain.repository

import com.example.cardlinker.domain.models.Account
import kotlinx.coroutines.flow.Flow

interface GetAccountRepository {
    fun getAccount(email: String): Flow<Account>
}