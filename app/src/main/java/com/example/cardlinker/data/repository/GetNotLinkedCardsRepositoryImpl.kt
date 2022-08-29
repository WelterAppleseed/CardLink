package com.example.cardlinker.data.repository

import com.example.cardlinker.data.local.dao.CardDao
import com.example.cardlinker.domain.models.Card
import com.example.cardlinker.domain.repository.GetNotLinkedCardsRepository
import com.example.cardlinker.util.objects.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotLinkedCardsRepositoryImpl @Inject constructor(private val cardDao: CardDao): GetNotLinkedCardsRepository {
    override fun getNotLinkedCards(): Flow<List<Card>> {
        return cardDao.getNotLinkedToAccountCards()
    }

}