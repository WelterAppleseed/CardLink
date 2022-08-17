package com.example.cardlinker.presentation.fragments.usercards.card_initializing.styles

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cardlinker.R
import com.example.cardlinker.databinding.StyleItemBinding
import com.example.cardlinker.domain.models.Style
import com.example.cardlinker.domain.models.StyleItem
import com.example.cardlinker.util.objects.StyleItems
import com.example.cardlinker.util.objects.Styles

class CardStylesAdapter(private val onStyleClickedListener: OnStyleClickedListener): RecyclerView.Adapter<CardStylesAdapter.ViewHolder>() {
    companion object {
        private val stylesIcons = arrayListOf(StyleItems.MARKET.apply { pressed = true }, StyleItems.COSMETIC, StyleItems.CLOTHES, StyleItems.TECHNICAL)
    }
    inner class ViewHolder(private val binding: StyleItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(style: StyleItem) {
            binding.apply {
                styleItem.text = style.styleName
                styleItem.setTextColor(if (style.pressed) ResourcesCompat.getColor(styleItem.resources, R.color.primaryRedColorBright, null) else ResourcesCompat.getColor(styleItem.resources, R.color.text_color_bright, null))
                styleItem.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(styleItem.context, style.styleIcon), null, null)
                TextViewCompat.setCompoundDrawableTintList(styleItem, ColorStateList.valueOf(ContextCompat.getColor(styleItem.context, if (style.pressed) R.color.primaryRedColor else R.color.grey3)))
                styleItem.setOnClickListener {
                    for (styleIcon in stylesIcons) {
                        if (styleIcon.pressed) {
                            styleIcon.pressed = false
                            notifyItemChanged(stylesIcons.indexOf(styleIcon))
                        }
                    }
                    style.pressed = true
                    onStyleClickedListener.onStyleClicked(style.styleName)
                    notifyItemChanged(stylesIcons.indexOf(style))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            StyleItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(stylesIcons[position])
    }

    override fun getItemCount(): Int {
       return stylesIcons.size
    }
}