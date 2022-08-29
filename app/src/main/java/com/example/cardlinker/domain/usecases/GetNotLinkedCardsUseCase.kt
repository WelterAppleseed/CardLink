package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.models.Card
import com.example.cardlinker.domain.repository.GetNotLinkedCardsRepository
import com.example.cardlinker.domain.usecases.base.BaseReturnUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotLinkedCardsUseCase @Inject constructor(private val getNotLinkedCardsRepository: GetNotLinkedCardsRepository): BaseReturnUseCase<Flow<List<Card>>> {
    override fun execute(): Flow<List<Card>> {
        return getNotLinkedCardsRepository.getNotLinkedCards()
    }

}