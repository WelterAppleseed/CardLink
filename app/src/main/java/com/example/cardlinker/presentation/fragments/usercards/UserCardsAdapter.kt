package com.example.cardlinker.presentation.fragments.usercards

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cardlinker.R
import com.example.cardlinker.databinding.ItemCardRecyclerBinding
import com.example.cardlinker.domain.models.Card
import com.example.cardlinker.presentation.base.text_watchers.OnCardClickListener

class UserCardsAdapter(private val cardList: List<Card>, onCardClicked: OnCardClickListener) :
    RecyclerView.Adapter<UserCardsAdapter.ViewHolder>() {
    private val onCardClickedListener = onCardClicked
    private var mutableCardList = ArrayList(cardList)
    inner class ViewHolder(private val binding: ItemCardRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(card: Card) {
            println(R.drawable.okei)
            binding.cardItem.setImageResource(card.background)
            binding.cardItem.setOnClickListener {
                onCardClickedListener.onCardClicked(card)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCardRecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    fun filter(name: String) {
        mutableCardList = arrayListOf()
        if (name != "") {
            for (card in cardList) {
                if (card.name?.startsWith(name, true) == true) mutableCardList.add(card)
            }
        } else {
            mutableCardList.addAll(cardList)
        }
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mutableCardList[position])
    }

    override fun getItemCount(): Int {
        return mutableCardList.size
    }
}