package com.example.cardlinker.data.repository

import android.content.SharedPreferences
import com.example.cardlinker.domain.repository.GetCurrentEmailRepository
import com.example.cardlinker.util.objects.Constants

class GetCurrentEmailRepositoryImpl(private val sharedPreferences: SharedPreferences): GetCurrentEmailRepository {
    override fun getCurrentEmail(): String {
        return sharedPreferences.getString(Constants.CURRENT_EMAIL, "")!!
    }

}