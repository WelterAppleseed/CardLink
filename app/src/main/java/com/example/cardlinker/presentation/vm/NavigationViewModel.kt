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
    fun goToUserCardsFragment() = router.navigateTo(Screens.userCardsFragment())

    fun goToEnterBannersFragment() = router.navigateTo(Screens.enterBannersFragment())

    fun goToCardInitializingFragment() = router.navigateTo(Screens.cardInitializingFragment())

    fun goToCameraFragment() = router.navigateTo(Screens.cameraFragment())

    fun goToSupportedCardsFragment() = router.navigateTo(Screens.supportedCardsFragment())

    fun goToMenuFragment() = router.navigateTo(Screens.menuFragment())

    fun goToProfileFragment() = router.navigateTo(Screens.profileFragment())

    fun goToCardsManagementFragment() = router.navigateTo(Screens.cardsManagementFragment())

    fun goToSettingsFragment() = router.navigateTo(Screens.settingsFragment())

    fun goToOldPasswordChangeFragment() = router.navigateTo(Screens.oldPasswordChangeFragment())

    fun goToNewPasswordChangeFragment() = router.navigateTo(Screens.newPasswordChangeFragment())

    fun goToPatternLockFragment() = router.navigateTo(Screens.patternLockFragment())

    fun goToEnterPatternFragment() = router.navigateTo(Screens.enterPatternFragment())
}