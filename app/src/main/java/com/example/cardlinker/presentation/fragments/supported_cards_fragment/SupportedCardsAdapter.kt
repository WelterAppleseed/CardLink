package com.example.cardlinker.presentation.fragments.supported_cards_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cardlinker.databinding.ItemSupportedCardBinding
import com.example.cardlinker.databinding.RecommendationItemBinding
import com.example.cardlinker.domain.models.Recommendation
import com.example.cardlinker.presentation.fragments.usercards.recommendations.RecommendationAdapter
import com.example.cardlinker.util.objects.Constants

class SupportedCardsAdapter(private val supportedList: List<Recommendation>) :
    RecyclerView.Adapter<SupportedCardsAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemSupportedCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recommendation: Recommendation) {
            binding.supCardIv.setImageResource(recommendation.marketImage)
            binding.supCardName.text = recommendation.marketName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSupportedCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(supportedList[position])
    }

    override fun getItemCount(): Int {
        return supportedList.size
    }
}