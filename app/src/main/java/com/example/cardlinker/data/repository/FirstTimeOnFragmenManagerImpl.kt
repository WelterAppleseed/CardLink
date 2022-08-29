package com.example.cardlinker.data.repository

import android.content.SharedPreferences
import com.example.cardlinker.domain.repository.FirstTimeOnFragmentManager
import javax.inject.Inject

class FirstTimeOnFragmenManagerImpl @Inject constructor(private val sharedPreferences: SharedPreferences): FirstTimeOnFragmentManager {
    override fun getFirstTimeOnFragment(fragmentName: String): Boolean {
        return sharedPreferences.getBoolean(fragmentName, true)
    }

    override fun updateFirstTimeOnFragment(fragmentName: String) {
        sharedPreferences.edit().putBoolean(fragmentName, false).apply()
    }

}