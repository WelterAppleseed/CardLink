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
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.example.cardlinker.R
import com.example.cardlinker.domain.models.Style
import com.example.cardlinker.presentation.base.text_watchers.CardTextWatcher
import com.example.cardlinker.util.disable

class CreditCardView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    private var background: ImageView
    private var foreground: ImageView
    private var cardNumberTv: TextView
    private var cardNameEt: EditText
    private var style: Style? = null
    init {
        LayoutInflater.from(context).inflate(R.layout.including_card_view, this, true)
        val attributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CreditCardView)
        background = findViewById(R.id.card_background_iv)
        foreground = findViewById(R.id.card_foreground_iv)
        cardNumberTv = findViewById(R.id.card_number)
        cardNameEt = findViewById(R.id.card_name_et)
        attributes.recycle()
    }
    fun getStyle(): Style? = style
    fun changeStyle(style: Style) {
        background.background = ContextCompat.getDrawable(context, style.cardBackground)
        foreground.background = ContextCompat.getDrawable(context, style.cardForeground)
        this.style = style
    }
    fun setForegroundVisibility(visibility: Int) {
        foreground.visibility = visibility
    }
    fun setNonEditableState() {
        cardNameEt.disable()
        cardNumberTv.disable()
    }
    fun getCardNumber(): Long {
        return cardNumberTv.text.toString().toLong()
    }
    fun getCardTitle(): String {
        return cardNameEt.text.toString()
    }
    fun setSrc(source: Int) {
        background.background = ContextCompat.getDrawable(context, source)
    }
    fun setCardName(name: String) {
        cardNameEt.setText(name)
    }
    fun connectWithTextView(textView: TextView){
        cardNameEt.doOnTextChanged { text, start, before, count ->
            textView.text = text
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