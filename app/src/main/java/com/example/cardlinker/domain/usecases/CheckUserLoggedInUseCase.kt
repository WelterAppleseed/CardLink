package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.repository.UserLoggedInManager
import com.example.cardlinker.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class CheckUserLoggedInUseCase @Inject constructor(private val userLoggedInManager: UserLoggedInManager): BaseUseCase<Boolean> {

    override fun execute(): Boolean {
        return userLoggedInManager.checkLoginState()
    }

}