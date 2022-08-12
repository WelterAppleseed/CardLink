package com.example.cardlinker.data.repository

import android.content.SharedPreferences
import com.example.cardlinker.domain.repository.FirstTimeUsedManager
import com.example.cardlinker.util.objects.Constants

class FirstTimeUsedImpl(private val sharedPreferences: SharedPreferences): FirstTimeUsedManager {
    override fun checkFirstTimeUsed(): Boolean {
        val state = sharedPreferences.getBoolean(Constants.FIRST_TIME_USED, true)
        return state
    }

    override fun isFirstTimeUsed(isFirstTime: Boolean) {
        sharedPreferences.edit().putBoolean(Constants.FIRST_TIME_USED, isFirstTime).apply()
    }

}