package com.example.cardlinker.util.objects

import com.example.cardlinker.presentation.fragments.usercards.camera.CameraFragment
import com.example.cardlinker.presentation.fragments.enter_banners.EnterBannersFragment
import com.example.cardlinker.presentation.fragments.menu.MenuFragment
import com.example.cardlinker.presentation.fragments.menu.cards_management.CardsManagementFragment
import com.example.cardlinker.presentation.fragments.menu.profile.ProfileFragment
import com.example.cardlinker.presentation.fragments.menu.settings.SettingsFragment
import com.example.cardlinker.presentation.fragments.menu.settings.password_change.new_password.NewPasswordChangeFragment
import com.example.cardlinker.presentation.fragments.menu.settings.password_change.old_password.OldPasswordChangeFragment
import com.example.cardlinker.presentation.fragments.supported_cards_fragment.SupportedCardsFragment
import com.example.cardlinker.presentation.fragments.usercards.UserCardsFragment
import com.example.cardlinker.presentation.fragments.usercards.card_initializing.CardInitializingFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun userCardsFragment() = FragmentScreen { UserCardsFragment() }
    fun enterBannersFragment() = FragmentScreen { EnterBannersFragment() }
    fun cardInitializingFragment() = FragmentScreen { CardInitializingFragment() }
    fun cameraFragment() = FragmentScreen { CameraFragment() }
    fun supportedCardsFragment() = FragmentScreen { SupportedCardsFragment() }
    fun menuFragment() = FragmentScreen { MenuFragment() }
    fun profileFragment() = FragmentScreen { ProfileFragment() }
    fun cardsManagementFragment() = FragmentScreen { CardsManagementFragment() }
    fun settingsFragment() = FragmentScreen { SettingsFragment() }
    fun oldPasswordChangeFragment() = FragmentScreen { OldPasswordChangeFragment() }
    fun newPasswordChangeFragment() = FragmentScreen { NewPasswordChangeFragment() }
}