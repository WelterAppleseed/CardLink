package com.example.cardlinker.data.repository

import android.content.SharedPreferences
import com.example.cardlinker.domain.repository.FirstTimeOnFragmentRepository
import javax.inject.Inject

class FirstTimeOnFragmentRepositoryImpl @Inject constructor(private val sharedPreferences: SharedPreferences): FirstTimeOnFragmentRepository {
    override fun getFirstTimeOnFragment(fragmentName: String): Boolean {
        return sharedPreferences.getBoolean(fragmentName, true)
    }

    override fun updateFirstTimeOnFragment(fragmentName: String) {
        sharedPreferences.edit().putBoolean(fragmentName, false).apply()
    }

}