package com.example.cardlinker.data.repository

import android.content.SharedPreferences
import com.example.cardlinker.domain.repository.GetCurrentEncodedPasswordRepository
import com.example.cardlinker.util.objects.Constants

class GetCurrentEncodedPasswordRepositoryImpl(private val sharedPreferences: SharedPreferences): GetCurrentEncodedPasswordRepository {
    override fun getEncodedPassword(): String {
        return sharedPreferences.getString(Constants.ENCODED_PASSWORD, "")!!
    }

}