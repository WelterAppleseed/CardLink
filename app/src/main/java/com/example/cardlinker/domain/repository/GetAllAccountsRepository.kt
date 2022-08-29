package com.example.cardlinker.domain.repository

import com.example.cardlinker.domain.models.Account
import kotlinx.coroutines.flow.Flow

interface GetAllAccountsRepository {
    suspend fun getAllAccounts(): Flow<List<Account>>
}