package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.models.Recommendation
import com.example.cardlinker.domain.repository.GetRecommendationRepository
import com.example.cardlinker.domain.usecases.base.BaseReturnUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecommendationReturnUseCase @Inject constructor(private val getRecommendationRepository: GetRecommendationRepository): BaseReturnUseCase<Flow<List<Recommendation>>> {
    override suspend fun execute(): Flow<List<Recommendation>> {
        return getRecommendationRepository.getRecommendations()
    }
}