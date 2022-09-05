package com.example.cardlinker.data.repository

import com.example.cardlinker.data.local.dao.CardDao
import com.example.cardlinker.domain.repository.DeleteCardsRepository
import javax.inject.Inject

class DeleteCardsRepositoryImpl @Inject constructor(private val cardDao: CardDao): DeleteCardsRepository {
    override suspend fun deleteCards(accountHashCode: Int) {
        cardDao.deleteCards(accountHashCode)
    }

}