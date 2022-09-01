package com.example.cardlinker.util

import java.lang.StringBuilder

fun String?.concatPassword(): String {
    return if (this != null && this.length >=4) {
        if (this.endsWith("    ", ignoreCase = true)) {
            StringBuilder(this).substring(0, this.length-4).toString()
        } else {
            this
        }
    } else {
        ""
    }
}