package com.example.cardlinker.domain.repository

import com.example.cardlinker.domain.models.Recommendation

interface AddRecommendationRepository {
    suspend fun addRecommendations(recommendations: List<Recommendation>)
}