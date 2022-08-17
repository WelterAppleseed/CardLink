package com.example.cardlinker.presentation.base.codeformatter

import com.example.cardlinker.domain.models.Code
import com.google.mlkit.vision.barcode.common.Barcode

interface OnCodeFormattedListener {
    fun onCodeFormatted(code: Code?)
}