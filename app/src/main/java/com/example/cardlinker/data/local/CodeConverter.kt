package com.example.cardlinker.data.local

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.cardlinker.domain.models.Code
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@TypeConverters
class CodeConverter {
    @TypeConverter
    fun fromCode(code: Code): String {
        return Gson().toJson(code)
    }
    @TypeConverter
    fun fromCodes(codes: List<Code>): String {
        return Gson().toJson(codes)
    }
    @TypeConverter
    fun toCode(string: String): Code {
        return Gson().fromJson(string, Code::class.java)
    }
    @TypeConverter
    fun toCodes(string: String): List<Code> {
        val myType = object : TypeToken<List<Code>>() {}.type
        val codes = Gson().fromJson<List<Code>>(string, myType)
        return codes
    }
}