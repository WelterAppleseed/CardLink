package com.example.cardlinker.domain.repository

import com.example.cardlinker.domain.models.Card
import kotlinx.coroutines.flow.Flow

interface CardRepository {
    fun getCardRepository(): Flow<List<Card>>

}