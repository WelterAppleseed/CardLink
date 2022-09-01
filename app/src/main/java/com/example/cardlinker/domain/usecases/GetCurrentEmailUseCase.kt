package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.repository.GetCurrentEmailRepository
import com.example.cardlinker.domain.usecases.base.BaseReturnUseCase
import javax.inject.Inject

class GetCurrentEmailUseCase @Inject constructor(private val getCurrentEmailRepository: GetCurrentEmailRepository): BaseReturnUseCase<String> {
    override fun execute(): String {
       return getCurrentEmailRepository.getCurrentEmail()
    }
}