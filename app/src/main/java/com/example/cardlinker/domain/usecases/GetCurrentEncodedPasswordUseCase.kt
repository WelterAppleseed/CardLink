package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.repository.GetCurrentEncodedPasswordRepository
import com.example.cardlinker.domain.usecases.base.BaseReturnUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentEncodedPasswordUseCase @Inject constructor(private val getCurrentEncodedPasswordRepository: GetCurrentEncodedPasswordRepository): BaseReturnUseCase<String> {
    override fun execute(): String {
       return getCurrentEncodedPasswordRepository.getEncodedPassword()
    }
}