package com.example.cardlinker.presentation.fragments.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cardlinker.databinding.ItemMenuBinding
import com.example.cardlinker.util.enums.CardMenuItem

class MenuFragmentAdapter(private val onMenuItemClickedListener: OnMenuItemClickedListener): RecyclerView.Adapter<MenuFragmentAdapter.ViewHolder>() {
    companion object {
        private val MENU_ITEMS = CardMenuItem.values()
    }
    inner class ViewHolder(private val binding: ItemMenuBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(cardMenuItem: CardMenuItem) {
            binding.apply {
                menuFragmentItem.text = cardMenuItem.text
                menuFragmentItem.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(menuFragmentItem.context, cardMenuItem.icon), null, null, null)
                menuFragmentItem.setOnClickListener {
                    onMenuItemClickedListener.onMenuItemClicked(cardMenuItem)
                }
                if (MENU_ITEMS.indexOf(cardMenuItem) == MENU_ITEMS.size-1) {
                    menuFragmentItem.background = null
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(MENU_ITEMS[position])
    }

    override fun getItemCount(): Int {
        return MENU_ITEMS.size
    }

}