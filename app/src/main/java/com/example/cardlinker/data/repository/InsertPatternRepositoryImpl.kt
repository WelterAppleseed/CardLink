package com.example.cardlinker.data.repository

import android.content.SharedPreferences
import com.example.cardlinker.domain.repository.InsertPatternRepository
import com.example.cardlinker.util.objects.Constants
import javax.inject.Inject

class InsertPatternRepositoryImpl @Inject constructor(private val sharedPreferences: SharedPreferences): InsertPatternRepository {
    override fun insertPattern(pattern: String) {
        sharedPreferences.edit().putString(Constants.PATTERN, pattern).apply()
    }


}