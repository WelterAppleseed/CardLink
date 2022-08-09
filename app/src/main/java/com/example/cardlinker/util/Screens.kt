package com.example.cardlinker.util

import com.example.cardlinker.presentation.fragments.enter_banners.EnterBannersFragment
import com.example.cardlinker.presentation.fragments.usercards.UserCardsFragment
import com.example.cardlinker.presentation.fragments.usercards.card_initializing.CardInitializingFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun userCardsFragment() = FragmentScreen { UserCardsFragment() }
    fun enterBannersFragment() = FragmentScreen { EnterBannersFragment() }
    fun cardInitializingFragment() = FragmentScreen { CardInitializingFragment() }
}