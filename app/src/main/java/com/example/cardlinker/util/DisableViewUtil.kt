package com.example.cardlinker.util

import android.view.View

fun View.disable() {
    this.isEnabled = false
    this.isFocusable = false
    this.isClickable = false
}
fun View.enable() {
    this.isEnabled = true
    this.isFocusable = true
    this.isClickable = true
}