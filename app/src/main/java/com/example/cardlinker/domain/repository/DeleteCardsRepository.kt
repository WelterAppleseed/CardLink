package com.example.cardlinker.domain.repository

interface DeleteCardsRepository {
    suspend fun deleteCards(accountHashCode: Int)
}