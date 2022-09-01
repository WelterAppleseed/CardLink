package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.repository.GetPatternRepository
import com.example.cardlinker.domain.usecases.base.BaseReturnUseCase
import javax.inject.Inject

class GetPatternUseCase @Inject constructor(private val getPatternRepository: GetPatternRepository): BaseReturnUseCase<String> {
    override fun execute(): String {
        return getPatternRepository.getPattern()
    }

}