package com.example.cardlinker.domain.repository

interface CheckIsLoggedInManager {
    fun checkIsLoggedIn(): Boolean
    fun updateIsLoggedIn(isLoggedIn: Boolean)
}