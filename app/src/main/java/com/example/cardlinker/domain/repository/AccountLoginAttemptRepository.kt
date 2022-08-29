package com.example.cardlinker.domain.repository


import com.example.cardlinker.domain.models.Account
import kotlinx.coroutines.flow.Flow

interface AccountLoginAttemptRepository {
    fun loginAttempt(nickOrEmail: String, encodedPassword: String): Flow<Account?>
}