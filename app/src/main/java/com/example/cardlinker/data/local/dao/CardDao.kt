package com.example.cardlinker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cardlinker.domain.models.Card
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    @Query("SELECT * from card")
    abstract fun getCards(): Flow<List<Card>>
    @Insert
    abstract fun insertCard(card: Card)
}