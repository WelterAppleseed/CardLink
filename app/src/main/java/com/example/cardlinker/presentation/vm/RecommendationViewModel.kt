package com.example.cardlinker.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cardlinker.R
import com.example.cardlinker.data.local.dao.RecommendationDao
import com.example.cardlinker.domain.models.Recommendation
import com.example.cardlinker.domain.usecases.AddRecommendationReturnUseCase
import com.example.cardlinker.domain.usecases.GetRecommendationReturnUseCase
import com.example.cardlinker.presentation.base.BaseViewModel
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
                    if (it.isEmpty()) addRecommendations()
                    recommendations.value = it
                }
        }
    }

    fun getRecommendations() = recommendations
    private fun addRecommendations() {
        viewModelScope.launch {
            val list = listOf(
                Recommendation(
                marketName = "O'KEI",
                    marketImage = R.drawable.okei,
                    reference = "1342"
                ),
                Recommendation(
                    marketName = "PYATEROCHKA",
                    marketImage = R.drawable.viruchai_pyat,
                    reference = "134fgv2"
                ),
                Recommendation(
                    marketName = "GAZPROM",
                    marketImage = R.drawable.gaz_prom,
                    reference = "1sdfgsdf342"
                ),
                Recommendation(
                    marketName = "NIKE",
                    marketImage = R.drawable.nike,
                    reference = "dfgsdfgsd2"
                ),
            )
            addRecommendationUseCase.saveInput(list)
            addRecommendationUseCase.execute()
        }
    }
}