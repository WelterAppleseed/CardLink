package com.example.cardlinker.util

import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import me.everything.android.ui.overscroll.IOverScrollDecor
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

fun RecyclerView.withAnimatedScrolling() {
    OverScrollDecoratorHelper.setUpOverScroll(
        this,
        OverScrollDecoratorHelper.ORIENTATION_VERTICAL
    )
}
fun RecyclerView.removeAnimatedScrolling() {
    OverScrollDecoratorHelper.setUpOverScroll(
        this,
        OverScrollDecoratorHelper.ORIENTATION_VERTICAL
    )
}
fun ScrollView.withAnimatedScrolling() {
    OverScrollDecoratorHelper.setUpOverScroll(this
    )
}
fun ViewPager.withAnimatedScrolling() {
    OverScrollDecoratorHelper.setUpOverScroll(this
    )
}