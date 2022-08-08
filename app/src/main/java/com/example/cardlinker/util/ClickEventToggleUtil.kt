package com.example.cardlinker.util

import android.view.View


fun View.disableClickEvent() {
    this.isClickable = false
    this.isFocusable = false
    this.isEnabled = false
}

fun View.enableClickEvent() {
    this.isClickable = true
    this.isFocusable = true
    this.isEnabled = true
}