package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.repository.InsertCurrentEncodedPasswordRepository
import com.example.cardlinker.domain.usecases.base.BaseSuspendVoidUseCase
import javax.inject.Inject

class InsertCurrentEncodedPasswordUseCase @Inject constructor(private val insertCurrentEncodedPasswordRepository: InsertCurrentEncodedPasswordRepository):
    BaseSuspendVoidUseCase {
    private var encodedPassword: String? = null
    fun saveInput(encodedPassword: String) = run { this.encodedPassword = encodedPassword }

    override suspend fun execute() {
        insertCurrentEncodedPasswordRepository.setPassword(encodedPassword!!)
    }
}