package com.example.cardlinker.domain.repository

interface GetCurrentEncodedPasswordRepository {
    fun getEncodedPassword(): String
}