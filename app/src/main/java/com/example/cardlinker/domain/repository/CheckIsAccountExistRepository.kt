package com.example.cardlinker.domain.repository

import kotlinx.coroutines.flow.Flow

interface CheckIsAccountExistRepository {
    fun checkIsExist(email: String): Flow<Boolean>
}