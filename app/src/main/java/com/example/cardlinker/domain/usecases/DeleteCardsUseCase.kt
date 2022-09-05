package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.repository.DeleteCardsRepository
import com.example.cardlinker.domain.usecases.base.BaseSuspendVoidUseCase
import javax.inject.Inject

class DeleteCardsUseCase @Inject constructor(private val deleteCardsRepository: DeleteCardsRepository): BaseSuspendVoidUseCase {

    private var accountHashCode: Int? = null

    fun saveInput(accountHashCode: Int) = run { this.accountHashCode = accountHashCode }

    override suspend fun execute() {
       deleteCardsRepository.deleteCards(accountHashCode!!)
    }

}