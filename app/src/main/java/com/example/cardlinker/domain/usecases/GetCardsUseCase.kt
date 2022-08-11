package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.models.Card
import com.example.cardlinker.domain.repository.GetCardRepository
import com.example.cardlinker.domain.usecases.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(private val getCardRepository: GetCardRepository): BaseUseCase<Flow<List<Card>>> {

    suspend fun saveCard(card: Card) = getCardRepository.saveInput(card)
    override suspend fun execute(): Flow<List<Card>> {
        return getCardRepository.getCardRepository()
    }
}