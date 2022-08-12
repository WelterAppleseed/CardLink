package com.example.cardlinker.domain.repository

import com.example.cardlinker.domain.models.Recommendation
import kotlinx.coroutines.flow.Flow

interface GetRecommendationRepository {
    fun getRecommendations(): Flow<List<Recommendation>>
}