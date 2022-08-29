package com.example.cardlinker.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cardlinker.domain.models.Recommendation
import com.example.cardlinker.domain.usecases.AddRecommendationReturnUseCase
import com.example.cardlinker.domain.usecases.GetRecommendationReturnUseCase
import com.example.cardlinker.presentation.base.BaseViewModel
import com.example.cardlinker.presentation.fragments.usercards.CardBackground
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendationViewModel @Inject constructor(
    private val getRecommendationUseCase: GetRecommendationReturnUseCase,
    private val addRecommendationUseCase: AddRecommendationReturnUseCase
) : BaseViewModel() {
    private val recommendations = MutableLiveData<List<Recommendation>>()

    init {
        updateRecommendations()
    }

    private fun updateRecommendations() {
        viewModelScope.launch {
            getRecommendationUseCase.execute()
                .distinctUntilChanged()
                .collect {
                    if (it.isEmpty()) {
                        addRecommendations()
                    } else {
                        recommendations.value = it
                    }
                }
        }
    }

    fun getRecommendations() = recommendations
    private fun addRecommendations() {
        viewModelScope.launch {
            val list = CardBackground.getSupportedCardsList()
            recommendations.value = list
            addRecommendationUseCase.saveInput(list)
            addRecommendationUseCase.execute()
        }
    }
}