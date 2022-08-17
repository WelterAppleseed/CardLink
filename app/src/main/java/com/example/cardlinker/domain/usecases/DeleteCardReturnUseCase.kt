package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.models.Code
import com.example.cardlinker.domain.repository.DeleteCardRepository
import com.example.cardlinker.domain.usecases.base.BaseVoidUseCase
import javax.inject.Inject

class DeleteCardReturnUseCase @Inject constructor(private val deleteCardRepository: DeleteCardRepository) :
    BaseVoidUseCase {
    private var inputCode: String? = null

    fun saveInput(cardNumber: String) = run { inputCode = cardNumber }

    override suspend fun execute() {
        deleteCardRepository.deleteCard(inputCode!!)
    }
}