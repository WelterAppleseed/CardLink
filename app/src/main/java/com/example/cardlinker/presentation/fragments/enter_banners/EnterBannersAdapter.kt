package com.example.cardlinker.presentation.fragments.enter_banners

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cardlinker.databinding.ItemEnterBannerBinding
import com.example.cardlinker.domain.models.BannerItem

class EnterBannersAdapter(private val list: List<BannerItem>) :
    RecyclerView.Adapter<EnterBannersAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemEnterBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(banner: BannerItem) {
            binding.bannerIv.setImageResource(banner.image)
            binding.bannerTv.text = banner.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemEnterBannerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}