package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.models.Account
import com.example.cardlinker.domain.repository.GetAccountRepository
import com.example.cardlinker.domain.usecases.base.BaseReturnUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccountUseCase @Inject constructor(private val getAccountRepository: GetAccountRepository) :
    BaseReturnUseCase<Flow<Account>> {
    private var email: String? = null
    fun saveInput(email: String) = run { this.email = email }
    override fun execute(): Flow<Account> {
        return getAccountRepository.getAccount(email!!)
    }

}