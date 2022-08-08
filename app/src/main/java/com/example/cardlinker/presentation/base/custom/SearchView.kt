package com.example.cardlinker.presentation.base.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
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
}