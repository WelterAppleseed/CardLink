package com.example.cardlinker.data.local.dao

import androidx.room.*
import com.example.cardlinker.domain.models.Card
import com.example.cardlinker.domain.models.Code
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    @Query("SELECT * from card")
    fun getCards(): Flow<List<Card>>
    @Query("UPDATE card SET accountHashCode=:newAccountHashCode WHERE accountHashCode=:oldAccountHashCode")
    suspend fun updateAccountHashcodeReference(oldAccountHashCode: Int, newAccountHashCode: Int)
    @Insert
    suspend fun insertCard(card: Card)
    @Query("DELETE FROM card WHERE number=:number")
    suspend fun deleteCard(number: String)
    @Query("SELECT * FROM card WHERE accountHashCode =:accountHashCode")
    fun getLinkedToAccountCards(accountHashCode: Int): Flow<List<Card>>
    @Query("SELECT * FROM card WHERE accountHashCode=0")
    fun getNotLinkedToAccountCards(): Flow<List<Card>>
}