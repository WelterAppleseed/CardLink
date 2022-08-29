package com.example.cardlinker.presentation.fragments.menu.cards_management

import com.example.cardlinker.domain.models.Card

interface OnSmallCardDeleteIconClickedListener {
    fun onDeleteClicked(card: Card, accountHashCode: Int)
}