package com.example.cardlinker.data.repository

import com.example.cardlinker.data.local.dao.CardDao
import com.example.cardlinker.domain.models.Card
import com.example.cardlinker.domain.models.Code
import com.example.cardlinker.domain.repository.GetLinkedCardsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLinkedCardsRepositoryImpl @Inject constructor(private val cardDao: CardDao): GetLinkedCardsRepository {
    override fun getLinkedCards(accountHashCode: Int): Flow<List<Card>> {
        return cardDao.getLinkedToAccountCards(accountHashCode)
    }

}