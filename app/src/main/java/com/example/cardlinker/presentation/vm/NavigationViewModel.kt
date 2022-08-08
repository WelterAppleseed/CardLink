package com.example.cardlinker.presentation.vm

import com.example.cardlinker.domain.usecases.CheckUserLoggedInUseCase
import com.example.cardlinker.presentation.base.BaseViewModel
import com.example.cardlinker.util.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.github.terrakok.cicerone.Router

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val router: Router,
    private val userLoggedInUseCase: CheckUserLoggedInUseCase
): BaseViewModel() {

    fun goToUserCardsFragment() = router.newRootChain(Screens.userCardsFragment())

    fun goToEnterBannersFragment() = router.newRootChain(Screens.enterBannerdsFragment())
}