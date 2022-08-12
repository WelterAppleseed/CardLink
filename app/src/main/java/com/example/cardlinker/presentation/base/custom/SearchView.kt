package com.example.cardlinker.presentation.base.custom

import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import com.example.cardlinker.R

class SearchView(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {
    private var searchField: EditText
    init {
        LayoutInflater.from(context).inflate(R.layout.search_view, this, true)
        searchField = findViewById(R.id.search_field)
        val attributesArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.SearchView)
        attributesArray.recycle()
    }
    fun setOnFocusChangeListener(ifFocused: (() -> Unit), ifNotFocused: (() -> Unit) ) {
        searchField.setOnFocusChangeListener { _, b ->
            if (b) {
                ifFocused()
            } else {
                ifNotFocused()
            }
        }
    }
    fun setOnTextChangedListener(ifChanged: ((changedPart: String) -> Unit)) {
        searchField.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


            }

            override fun afterTextChanged(p0: Editable?) {
                println(p0.toString())
                ifChanged(p0.toString())
            }
        })

    }
    fun removeFocus() {
        searchField.clearFocus()
    }
}