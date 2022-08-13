package com.example.cardlinker.presentation.vm

import com.example.cardlinker.presentation.base.BaseViewModel
import com.example.cardlinker.util.objects.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.github.terrakok.cicerone.Router

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val router: Router
): BaseViewModel() {
    fun goToUserCardsFragment() = router.newRootChain(Screens.userCardsFragment())

    fun goToEnterBannersFragment() = router.newRootChain(Screens.enterBannersFragment())

    fun goToCardInitializingFragment() = router.newRootChain(Screens.cardInitializingFragment())

    fun goToCameraFragment() = router.newRootChain(Screens.cameraFragment())

    fun goToSupportedCardsFragment() = router.newRootChain(Screens.supportedCardsFragment())
}