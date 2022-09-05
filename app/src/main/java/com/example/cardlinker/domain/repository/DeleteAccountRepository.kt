package com.example.cardlinker.domain.repository

import com.example.cardlinker.domain.models.Account

interface DeleteAccountRepository {
    suspend fun deleteAccount(email: String)
}