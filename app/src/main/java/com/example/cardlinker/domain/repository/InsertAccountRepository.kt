package com.example.cardlinker.domain.repository

import com.example.cardlinker.domain.models.Account

interface InsertAccountRepository {
    suspend fun insertAccount(account: Account)
}