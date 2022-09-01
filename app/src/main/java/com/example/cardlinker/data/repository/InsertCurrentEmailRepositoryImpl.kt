package com.example.cardlinker.data.repository

import android.content.SharedPreferences
import com.example.cardlinker.domain.repository.InsertCurrentEmailRepository
import com.example.cardlinker.util.objects.Constants
import javax.inject.Inject

class InsertCurrentEmailRepositoryImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
    InsertCurrentEmailRepository {
    override fun setEmail(email: String) {
        sharedPreferences.edit().putString(Constants.CURRENT_EMAIL, email).apply()
    }
}