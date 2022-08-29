package com.example.cardlinker.presentation.fragments.menu.cards_management

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cardlinker.databinding.ItemSmallCardBinding
import com.example.cardlinker.domain.models.Card

class CardsManagementAdapter(
    cards: List<Card>,
    private val onSmallCardDeleteIconClickedListener: OnSmallCardDeleteIconClickedListener
) : RecyclerView.Adapter<CardsManagementAdapter.ViewHolder>() {

    private val nestedCards = ArrayList(cards)

    inner class ViewHolder(private val binding: ItemSmallCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(card: Card) {
            binding.apply {
                smallCardIcon.apply {
                    if (card.style != null) {
                        background = ResourcesCompat.getDrawable(
                            resources,
                           card.style.cardBackground,
                            null
                        )
                        stylishGroup.visibility = View.VISIBLE
                            smallCardTitleIcon.text = card.name?.first()?.uppercase()
                    } else {
                        background = ResourcesCompat.getDrawable(
                            resources,
                            card.background,
                            null
                        )
                    }
                }
                smallCardTitle.text = card.name
                smallCardDelete.setOnClickListener {
                    onSmallCardDeleteIconClickedListener.onDeleteClicked(card, card.accountHashCode)
                    val position = nestedCards.indexOf(card)
                    nestedCards.remove(card)
                    notifyItemRemoved(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSmallCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(nestedCards[position])
    }

    override fun getItemCount(): Int {
        return nestedCards.size
    }
}