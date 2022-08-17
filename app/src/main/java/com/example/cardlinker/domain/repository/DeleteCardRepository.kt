package com.example.cardlinker.domain.repository



interface DeleteCardRepository {
    suspend fun deleteCard(cardNumber: String)
}