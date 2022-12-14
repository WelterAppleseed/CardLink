package com.example.cardlinker.domain.repository

import com.example.cardlinker.domain.models.Card
import kotlinx.coroutines.flow.Flow

interface GetCardRepository {
    suspend fun saveInput(card: Card)
    fun getCardRepository(): Flow<List<Card>>

}