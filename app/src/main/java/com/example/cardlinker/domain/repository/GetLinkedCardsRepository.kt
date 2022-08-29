package com.example.cardlinker.domain.repository

import com.example.cardlinker.domain.models.Card
import com.example.cardlinker.domain.models.Code
import kotlinx.coroutines.flow.Flow

interface GetLinkedCardsRepository {
    fun getLinkedCards(accountHashCode: Int): Flow<List<Card>>
}