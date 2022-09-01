package com.example.cardlinker.data.repository

import android.content.SharedPreferences
import com.example.cardlinker.domain.repository.GetPatternRepository
import com.example.cardlinker.util.objects.Constants
import javax.inject.Inject

class GetPatternRepositoryImpl @Inject constructor(private val sharedPreferences: SharedPreferences): GetPatternRepository {
    override fun getPattern(): String {
        return sharedPreferences.getString(Constants.PATTERN, "")!!
    }

}