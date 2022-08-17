package com.example.cardlinker.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cardlinker.data.local.CodeConverter
import com.example.cardlinker.data.local.dao.CardDao
import com.example.cardlinker.data.local.dao.RecommendationDao
import com.example.cardlinker.domain.models.Card
import com.example.cardlinker.domain.models.Recommendation

@TypeConverters(value = [CodeConverter::class])
@Database(entities = [Card::class, Recommendation::class], version = 1)
abstract class CardLinkDatabase: RoomDatabase() {
    abstract fun cardDao() : CardDao
    abstract fun recommendationDao(): RecommendationDao
}