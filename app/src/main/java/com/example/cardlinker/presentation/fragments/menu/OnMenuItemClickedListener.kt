package com.example.cardlinker.presentation.fragments.menu

import com.example.cardlinker.util.enums.CardMenuItem

interface OnMenuItemClickedListener {
    fun onMenuItemClicked(cardMenuItem: CardMenuItem)
}