package com.example.cardlinker.util.objects

import com.example.cardlinker.R
import com.example.cardlinker.domain.models.BannerItem

object EnterBanners {
    fun getBanners() = listOf(TAKE_PHOTO_OR_CHOOSE_IMAGE, INPUT_YOUR_DATA, CONNECT_YOUR_CARTS)
    private val TAKE_PHOTO_OR_CHOOSE_IMAGE = BannerItem(R.drawable.camera, "Take a photo of the code (barcode) of your discount card") //add another codes
    private val INPUT_YOUR_DATA = BannerItem(R.drawable.credit_cards, "Enter the data required to link the card to the application")
    private val CONNECT_YOUR_CARTS = BannerItem(R.drawable.connect, "Connect your discount cards and use them at any time")
}