package com.example.cardlinker.data.local

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.cardlinker.domain.models.Code
import com.google.gson.Gson

@TypeConverters
class CodeConverter {
    @TypeConverter
    fun fromCode(code: Code): String {
        return Gson().toJson(code)
    }
    @TypeConverter
    fun toCode(string: String): Code {
        return Gson().fromJson(string, Code::class.java)
    }
}