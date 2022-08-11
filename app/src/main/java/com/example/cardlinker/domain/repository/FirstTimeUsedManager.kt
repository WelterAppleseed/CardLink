package com.example.cardlinker.domain.repository

interface FirstTimeUsedManager {
    fun checkFirstTimeUsed(): Boolean
    fun isFirstTimeUsed(isLoggedIn: Boolean)
}