package com.example.cardlinker.presentation.base.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.cardlinker.R
import com.example.cardlinker.presentation.base.text_watchers.CardTextWatcher

class CreditCardView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    private var background: ImageView
    private var foreground: ImageView
    private var cardNumberTv: TextView
    private var cardNameEt: EditText
    init {
        LayoutInflater.from(context).inflate(R.layout.including_card_view, this, true)
        val attributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CreditCardView)
        background = findViewById(R.id.card_background_iv)
        foreground = findViewById(R.id.card_foreground_iv)
        cardNumberTv = findViewById(R.id.card_number)
        cardNameEt = findViewById(R.id.card_name_et)
        attributes.recycle()
    }
    private fun changeStyle(style: Int) {
    }
    fun getCardNumber(): Long {
        return cardNumberTv.text.toString().toLong()
    }
    fun getCardTitle(): String {
        return cardNameEt.text.toString()
    }
    fun setSrcAndName(pair: Pair<Int, String>?) {
        if (pair != null) {
            background.setImageResource(pair.first)
            cardNameEt.setText(pair.second)
        }
    }
    fun ifCardRecognizeError() {
        cardNameEt.visibility = View.VISIBLE
        foreground.visibility = View.VISIBLE
    }
    fun setCardNumber(cardNumber: String) {
        cardNumberTv.setText(cardNumber)
    }
}