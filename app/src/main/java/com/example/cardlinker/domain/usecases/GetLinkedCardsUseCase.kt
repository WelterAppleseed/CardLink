package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.models.Card
import com.example.cardlinker.domain.models.Code
import com.example.cardlinker.domain.repository.GetLinkedCardsRepository
import com.example.cardlinker.domain.usecases.base.BaseReturnUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLinkedCardsUseCase @Inject constructor(private val getLinkedCardsRepository: GetLinkedCardsRepository): BaseReturnUseCase<Flow<List<Card>>> {
    private var accountHashCode: Int? = null
    fun saveInput(accountHashCode: Int) = run { this.accountHashCode = accountHashCode }
    override fun execute(): Flow<List<Card>> {
        return getLinkedCardsRepository.getLinkedCards(accountHashCode!!)
    }

}