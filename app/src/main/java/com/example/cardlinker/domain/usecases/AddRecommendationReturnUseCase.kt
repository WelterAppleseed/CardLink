package com.example.cardlinker.domain.usecases

import com.example.cardlinker.domain.models.Recommendation
import com.example.cardlinker.domain.repository.AddRecommendationRepository
import com.example.cardlinker.domain.usecases.base.BaseVoidUseCase
import javax.inject.Inject

class AddRecommendationReturnUseCase @Inject constructor(private val addRecommendationRepository: AddRecommendationRepository):
    BaseVoidUseCase {
    private var recommendations: List<Recommendation>? = null
    fun saveInput(recommendations: List<Recommendation>) = run { this.recommendations = recommendations}
    override suspend fun execute(){
        return addRecommendationRepository.addRecommendations(recommendations!!)
    }
}