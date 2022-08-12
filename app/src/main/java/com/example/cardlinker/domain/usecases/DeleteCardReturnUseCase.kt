package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.repository.DeleteCardRepository
import com.example.cardlinker.domain.usecases.base.BaseReturnUseCase
import com.example.cardlinker.domain.usecases.base.BaseVoidUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class DeleteCardReturnUseCase @Inject constructor(private val deleteCardRepository: DeleteCardRepository) :
    BaseVoidUseCase {
    private var inputCode: String? = null

    fun saveInput(code: String) = run { inputCode = code }

    override suspend fun execute() {
        deleteCardRepository.deleteCard(inputCode!!)
    }
}