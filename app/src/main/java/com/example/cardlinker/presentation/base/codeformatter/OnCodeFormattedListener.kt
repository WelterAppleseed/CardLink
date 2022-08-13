package com.example.cardlinker.presentation.base.codeformatter

import com.google.mlkit.vision.barcode.common.Barcode

interface OnCodeFormattedListener {
    fun onCodeFormatted(codes: List<Barcode>)
}