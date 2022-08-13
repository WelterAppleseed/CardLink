package com.example.cardlinker.data.repository

import com.example.cardlinker.data.local.dao.RecommendationDao
import com.example.cardlinker.domain.models.Recommendation
import com.example.cardlinker.domain.repository.AddRecommendationRepository
import com.example.cardlinker.domain.repository.GetRecommendationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddRecommendationRepositoryImpl @Inject constructor(
    private val recommendationDao: RecommendationDao
): AddRecommendationRepository {
    override suspend fun addRecommendations(recommendations: List<Recommendation>) {
        recommendationDao.addRecommendations(recommendations)
    }
}