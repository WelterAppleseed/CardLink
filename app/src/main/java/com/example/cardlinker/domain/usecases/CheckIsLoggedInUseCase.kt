package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.repository.CheckIsLoggedInManager
import com.example.cardlinker.domain.usecases.base.BaseReturnUseCase
import javax.inject.Inject

class CheckIsLoggedInUseCase @Inject constructor(private val checkIsLoggedInManager: CheckIsLoggedInManager): BaseReturnUseCase<Boolean> {

    fun updateState(isLoggedIn: Boolean) = checkIsLoggedInManager.updateIsLoggedIn(isLoggedIn)

    override fun execute(): Boolean {
        return checkIsLoggedInManager.checkIsLoggedIn()
    }

}