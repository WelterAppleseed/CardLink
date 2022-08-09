package com.example.cardlinker.presentation.base.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.cardlinker.R
import com.example.cardlinker.presentation.base.text_watchers.CardTextWatcher

class CreditCardView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    private var background: FrameLayout
    private var foreground: ImageView
    private var cardNumberEt: EditText
    private var cardNameEt: EditText
    init {
        LayoutInflater.from(context).inflate(R.layout.including_card_view, this, true)
        val attributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CreditCardView)
        background = findViewById(R.id.card_background_iv)
        foreground = findViewById(R.id.card_foreground_iv)
        cardNumberEt = findViewById(R.id.card_number)
        cardNameEt = findViewById(R.id.card_name_et)
        cardNumberEt.addTextChangedListener(CardTextWatcher())
        attributes.recycle()
    }
    private fun changeStyle(style: Int) {
    }
    fun getCardNumber(): Long {
        return cardNumberEt.text.toString().toLong()
    }
    fun getCardTitle(): String {
        return cardNameEt.text.toString()
    }
    fun setCardNumber(cardNumber: String) {
        cardNumberEt.setText(cardNumber)
    }
}