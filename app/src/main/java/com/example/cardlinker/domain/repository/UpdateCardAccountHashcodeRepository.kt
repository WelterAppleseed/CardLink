package com.example.cardlinker.domain.repository

interface UpdateCardAccountHashcodeRepository {
    suspend fun updateWithAccountHashcode(newAccountHashCode: Int, oldAccountHashCode: Int)
}