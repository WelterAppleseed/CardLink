package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.repository.InsertCurrentEmailRepository
import com.example.cardlinker.domain.usecases.base.BaseSuspendVoidUseCase
import javax.inject.Inject

class InsertCurrentEmailUseCase @Inject constructor(private val insertCurrentEmailRepository: InsertCurrentEmailRepository):
    BaseSuspendVoidUseCase {
    private var email: String? = null
    fun saveInput(email: String) = run { this.email = email }

    override suspend fun execute() {
        insertCurrentEmailRepository.setEmail(email!!)
    }
}