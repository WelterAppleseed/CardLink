package com.example.cardlinker.util

import android.content.res.ColorStateList
import android.widget.EditText
import androidx.core.widget.TextViewCompat
import com.example.cardlinker.R

fun EditText.isWrong() {
    if (this.compoundDrawables.isNotEmpty()) {
        val red = this.context.getColor(R.color.primaryRedColor)
        this.setTextColor(red)
        this.backgroundTintList = ColorStateList.valueOf(red)
        this.setHintTextColor(this.context.getColor(R.color.primaryRedColorTransparency))
        TextViewCompat.setCompoundDrawableTintList(this, ColorStateList.valueOf(red))
    }
}

fun EditText.isNormal() {
    if (this.currentTextColor != this.context.getColor(R.color.white)) {
        if (this.compoundDrawables.isNotEmpty()) {
            val white = this.context.getColor(R.color.white)
            this.setTextColor(white)
            this.setHintTextColor(this.context.getColor(R.color.grey5))
            this.backgroundTintList = ColorStateList.valueOf(white)
            TextViewCompat.setCompoundDrawableTintList(this, ColorStateList.valueOf(this.context.getColor(R.color.grey1)))
        }
    }
}