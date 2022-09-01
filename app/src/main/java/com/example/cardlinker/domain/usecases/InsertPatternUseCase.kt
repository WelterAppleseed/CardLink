package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.repository.InsertPatternRepository
import com.example.cardlinker.domain.usecases.base.BaseSuspendVoidUseCase
import javax.inject.Inject

class InsertPatternUseCase @Inject constructor(private val insertPatternRepository: InsertPatternRepository): BaseSuspendVoidUseCase {

    private var pattern: String? = null

    fun saveInput(pattern: String) = run { this.pattern = pattern }

    override suspend fun execute() {
        insertPatternRepository.insertPattern(pattern!!)
    }
}