package com.example.cardlinker.domain.repository

interface UserLoggedInManager {
    fun checkLoginState(): Boolean
    fun updateLoginState(isLoggedIn: Boolean)
}