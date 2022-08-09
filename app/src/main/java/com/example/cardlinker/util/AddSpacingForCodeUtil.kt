package com.example.cardlinker.util

import java.lang.StringBuilder

fun String.codeWithSpaces(): String {
    val strb = StringBuilder(this)
    val charArray = arrayListOf<Int>()
    var index = 0
    for (i in 1..length) {
        if (i % 3 == 0) {
            charArray.add(i+index)
            index++
        }
    }
    charArray.forEach { strb.insert(it, " ") }
    return strb.toString()
}