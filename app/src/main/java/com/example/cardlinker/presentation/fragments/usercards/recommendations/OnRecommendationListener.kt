package com.example.cardlinker.presentation.fragments.usercards.recommendations

import com.example.cardlinker.domain.models.Recommendation

interface OnRecommendationListener {
    fun onRecommendationClicked(recommendation: Recommendation)
}