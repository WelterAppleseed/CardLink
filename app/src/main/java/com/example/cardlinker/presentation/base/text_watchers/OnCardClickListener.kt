package com.example.cardlinker.presentation.base.text_watchers

import com.example.cardlinker.domain.models.Card

interface OnCardClickListener {
    fun onCardClicked(card: Card) {

    }
}