package com.example.cardlinker.domain.repository

interface InsertCurrentEncodedPasswordRepository {
    fun setPassword(encodedPassword: String)
}