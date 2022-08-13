package com.example.cardlinker.data.repository

import com.example.cardlinker.data.local.dao.RecommendationDao
import com.example.cardlinker.domain.models.Recommendation
import com.example.cardlinker.domain.repository.GetRecommendationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecommendationRepositoryImpl @Inject constructor(
    private val recommendationDao: RecommendationDao
): GetRecommendationRepository {
    override fun getRecommendations(): Flow<List<Recommendation>> {
        return recommendationDao.getRecommendations()
    }

}