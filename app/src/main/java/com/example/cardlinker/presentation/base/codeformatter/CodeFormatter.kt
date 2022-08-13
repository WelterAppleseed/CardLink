package com.example.cardlinker.presentation.base.codeformatter

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class CodeFormatter {
    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
        .build()
    private var listener: OnCodeFormattedListener? = null
    private val scanner = BarcodeScanning.getClient(options)
    private var codeBitmap: Bitmap? = null
    private var codeUri: Uri? = null

    constructor(bitmap: Bitmap, onCodeFormattedListener: OnCodeFormattedListener) {
        codeBitmap = bitmap
        listener = onCodeFormattedListener
    }

    constructor(uri: Uri, onCodeFormattedListener: OnCodeFormattedListener) {
        codeUri = uri
        listener = onCodeFormattedListener
    }

    fun process(context: Context?) {
        if (listener != null) {
            when {
                codeBitmap != null -> {
                    scanner.process(InputImage.fromBitmap(codeBitmap!!, 0))
                        .addOnSuccessListener { barcodeList ->
                            listener!!.onCodeFormatted(barcodeList)
                        }
                        .addOnCanceledListener {
                            println("error")
                        }
                }
                codeUri != null && context != null -> {
                    scanner.process(InputImage.fromFilePath(context, codeUri!!))
                        .addOnSuccessListener { barcodeList ->
                            listener!!.onCodeFormatted(barcodeList)
                        }
                        .addOnCanceledListener {
                            println("error")
                        }
                }
                else -> {
                }
            }
        }
    }
}