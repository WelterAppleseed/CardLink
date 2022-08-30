package com.example.cardlinker.util

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat.getSystemService


private fun Activity.hideSoftKeyboard() {
    this.currentFocus?.let {
        val inputMethodManager =
            getSystemService(this, InputMethodManager::class.java)!!
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}
@SuppressLint("ClickableViewAccessibility")
fun Activity.setupUI(view: View) {
    // Set up touch listener for non-text box views to hide keyboard.
    if (view !is EditText && view !is Button && view !is AppCompatButton) {
        view.setOnTouchListener { v, event ->
            hideSoftKeyboard()
            false
        }
    }
    if (view is ViewGroup) {
        for (i in 0 until view.childCount) {
            val innerView = view.getChildAt(i)
            setupUI(innerView)
        }
    }
}