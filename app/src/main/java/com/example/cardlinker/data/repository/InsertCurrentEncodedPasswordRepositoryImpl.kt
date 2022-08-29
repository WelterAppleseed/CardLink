package com.example.cardlinker.data.repository

import android.content.SharedPreferences
import com.example.cardlinker.domain.repository.GetCurrentEncodedPasswordRepository
import com.example.cardlinker.domain.repository.InsertCurrentEncodedPasswordRepository
import com.example.cardlinker.util.objects.Constants
import javax.inject.Inject

class InsertCurrentEncodedPasswordRepositoryImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
    InsertCurrentEncodedPasswordRepository {
    override fun setPassword(encodedPassword: String) {
        sharedPreferences.edit().putString(Constants.ENCODED_PASSWORD, encodedPassword).apply()
    }
}