package com.example.cardlinker.util.objects

import com.example.cardlinker.R
import com.example.cardlinker.domain.models.BannerItem

object EnterBanners {
    fun getBanners() = listOf(TAKE_PHOTO_OR_CHOOSE_IMAGE, INPUT_YOUR_DATA, CONNECT_YOUR_CARTS)
    private val TAKE_PHOTO_OR_CHOOSE_IMAGE = BannerItem(R.drawable.credit_cards, "Сделайте фото кода (штрих-кода, QR-кода) вашей дисконтной карты")
    private val INPUT_YOUR_DATA = BannerItem(R.drawable.credit_cards, "Введите данные, необходимые для привязки карты к приложению")
    private val CONNECT_YOUR_CARTS = BannerItem(R.drawable.credit_cards, "Подключайте ваши дисконтные карты и используйте их в любое время")
}