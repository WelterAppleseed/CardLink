package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.models.Card
import com.example.cardlinker.domain.repository.CardRepository
import com.example.cardlinker.domain.usecases.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(private val cardRepository: CardRepository): BaseUseCase<Flow<List<Card>>> {

    fun saveCard(card: Card) = cardRepository.saveInput(card)
    override fun execute(): Flow<List<Card>> {
        return cardRepository.getCardRepository()
    }
}