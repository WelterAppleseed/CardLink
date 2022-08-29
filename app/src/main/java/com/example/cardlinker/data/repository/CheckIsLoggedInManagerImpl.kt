package com.example.cardlinker.data.repository

import android.content.SharedPreferences
import com.example.cardlinker.domain.repository.CheckIsLoggedInManager
import com.example.cardlinker.util.objects.Constants
import javax.inject.Inject

class CheckIsLoggedInManagerImpl @Inject constructor(private val sharedPreferences: SharedPreferences): CheckIsLoggedInManager {
    override fun checkIsLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(Constants.LOGIN_STATE, false)
    }

    override fun updateIsLoggedIn(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean(Constants.LOGIN_STATE, isLoggedIn).apply()
    }

}