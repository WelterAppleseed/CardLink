package com.example.cardlinker.presentation.fragments.usercards.recommendations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cardlinker.R
import com.example.cardlinker.databinding.RecommendationItemBinding
import com.example.cardlinker.domain.models.Recommendation
import com.example.cardlinker.util.objects.Constants

class RecommendationAdapter(private val recommendationList: List<Recommendation>) :
    RecyclerView.Adapter<RecommendationAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: RecommendationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recommendation: Recommendation) {
            binding.recomIv.setImageResource(recommendation.marketImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecommendationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recommendationList[position])

    }

    override fun getItemCount(): Int {
        return Constants.SUPPORT_CARD_SIZE
    }
}