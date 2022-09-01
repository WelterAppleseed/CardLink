package com.example.cardlinker.data.repository

import com.example.cardlinker.data.local.dao.CardDao
import com.example.cardlinker.domain.models.Code
import com.example.cardlinker.domain.repository.DeleteCardRepository
import javax.inject.Inject

class DeleteCardRepositoryImpl @Inject constructor(private val cardDao: CardDao): DeleteCardRepository {
    override suspend fun deleteCard(cardNumber: String) {
        cardDao.deleteCard(cardNumber)
    }


}