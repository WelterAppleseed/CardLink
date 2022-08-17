package com.example.cardlinker.data.local.dao

import androidx.room.*
import com.example.cardlinker.domain.models.Card
import com.example.cardlinker.domain.models.Code
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    @Query("SELECT * from card")
    fun getCards(): Flow<List<Card>>
    @Insert
    suspend fun insertCard(card: Card)
    @Query("DELETE FROM card WHERE number=:number")
    suspend fun deleteCard(number: String)
}