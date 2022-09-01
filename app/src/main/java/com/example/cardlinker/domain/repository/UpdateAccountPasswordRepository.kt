package com.example.cardlinker.domain.repository

import com.example.cardlinker.domain.models.Account

interface UpdateAccountPasswordRepository {
    suspend fun updateData(data: Pair<Account, String>)
}