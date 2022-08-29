package com.example.cardlinker.domain.repository

import com.example.cardlinker.domain.models.Account

interface UpdateAccountDataRepository {
    suspend fun updateData(data: Pair<Account, String>)
}