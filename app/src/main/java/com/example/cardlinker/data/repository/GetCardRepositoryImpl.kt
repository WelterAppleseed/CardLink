package com.example.cardlinker.data.repository

import com.example.cardlinker.data.local.dao.CardDao
import com.example.cardlinker.domain.models.Card
import com.example.cardlinker.domain.repository.GetCardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCardRepositoryImpl @Inject constructor(private val cardDao: CardDao): GetCardRepository {
    override suspend fun saveInput(card: Card) {
        cardDao.insertCard(card)
    }

    override fun getCardRepository(): Flow<List<Card>> {
        return cardDao.getCards()
    }
}