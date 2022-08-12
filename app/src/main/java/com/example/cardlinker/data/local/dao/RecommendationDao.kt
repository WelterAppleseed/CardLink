package com.example.cardlinker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.cardlinker.domain.models.Recommendation
import kotlinx.coroutines.flow.Flow

@Dao
interface RecommendationDao {
    @Insert
    suspend fun addRecommendations(recommendations: List<Recommendation>)
    @Insert
    fun addRecommendation(recommendation: Recommendation)
    @Query("SELECT * FROM recommendation")
    fun getRecommendations(): Flow<List<Recommendation>>
}