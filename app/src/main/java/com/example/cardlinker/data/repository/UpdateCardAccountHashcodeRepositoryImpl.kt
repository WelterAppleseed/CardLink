package com.example.cardlinker.data.repository

import com.example.cardlinker.data.local.dao.CardDao
import com.example.cardlinker.domain.repository.UpdateCardAccountHashcodeRepository
import javax.inject.Inject

class UpdateCardAccountHashcodeRepositoryImpl @Inject constructor(private val cardDao: CardDao): UpdateCardAccountHashcodeRepository {
    override suspend fun updateWithAccountHashcode(accountHashCode: Int) {
        cardDao.updateAccountHashcodeReference(accountHashCode)
    }

}