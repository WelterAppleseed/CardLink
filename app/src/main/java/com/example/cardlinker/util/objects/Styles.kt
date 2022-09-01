package com.example.cardlinker.util.objects

import com.example.cardlinker.R
import com.example.cardlinker.domain.models.Style

object Styles {
    val MARKET_STYLE = Style(styleName = "Market", cardBackground = R.drawable.market_card_background, cardForeground = R.drawable.grocery_cart)
    val TECH_STYLE = Style(styleName = "Tech", cardBackground = R.drawable.tech_card_background, cardForeground = R.drawable.usb)
    val CLOTHES_STYLE = Style(styleName = "Clothes", cardBackground = R.drawable.clothes_card_background, cardForeground = R.drawable.clothes)
    val COSMETIC_STYLE = Style(styleName = "Cosmetic", cardBackground = R.drawable.cosmetic_card_background, cardForeground = R.drawable.night_cream)
}