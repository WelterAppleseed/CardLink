package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.models.Account
import com.example.cardlinker.domain.repository.GetAllAccountsRepository
import com.example.cardlinker.domain.usecases.base.BaseReturnUseCase
import com.example.cardlinker.domain.usecases.base.BaseSuspendVoidUseCase
import com.example.cardlinker.domain.usecases.base.SuspendReturnUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllAccountsUseCase @Inject constructor(private val getAllAccountsRepository: GetAllAccountsRepository):
    SuspendReturnUseCase<Flow<List<Account>>> {
    override suspend fun execute(): Flow<List<Account>> {
        return getAllAccountsRepository.getAllAccounts()
    }

}