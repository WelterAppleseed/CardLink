package com.example.cardlinker.data.repository

import android.content.SharedPreferences
import com.example.cardlinker.domain.repository.UserLoggedInManager
import com.example.cardlinker.util.Constants

class UserLoggedInImpl(private val sharedPreferences: SharedPreferences): UserLoggedInManager {
    override fun checkLoginState(): Boolean {
        val state = sharedPreferences.getBoolean(Constants.LOGIN_STATE, false)
        return state
    }

    override fun updateLoginState(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean(Constants.LOGIN_STATE, isLoggedIn).apply()
    }

}