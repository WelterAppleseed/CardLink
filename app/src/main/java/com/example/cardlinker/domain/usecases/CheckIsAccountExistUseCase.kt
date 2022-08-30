package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.repository.CheckIsAccountExistRepository
import com.example.cardlinker.domain.usecases.base.BaseReturnUseCase
import com.example.cardlinker.domain.usecases.base.SuspendReturnUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckIsAccountExistUseCase @Inject constructor(private val checkIsAccountExistRepository: CheckIsAccountExistRepository): BaseReturnUseCase<Flow<Boolean>> {

    private var email: String? = null

    fun saveInput(email: String) = run { this.email = email }

    override fun execute(): Flow<Boolean> {
        return checkIsAccountExistRepository.checkIsExist(email!!)
    }

}