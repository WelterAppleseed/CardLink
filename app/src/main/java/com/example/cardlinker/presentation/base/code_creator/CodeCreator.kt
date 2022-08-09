package com.example.cardlinker.presentation.base.code_creator

import android.graphics.Bitmap
import androidx.annotation.ColorInt
import com.google.zxing.BarcodeFormat
import com.google.zxing.oned.CodaBarWriter

import com.google.zxing.oned.Code128Writer

class CodeCreator {
    companion object {
        fun createBarcodeBitmap(
            barcodeValue: String,
            @ColorInt barcodeColor: Int,
            @ColorInt backgroundColor: Int,
            widthPixels: Int,
            heightPixels: Int
        ): Bitmap {
            val bitMatrix = CodaBarWriter().encode(
                barcodeValue,
                BarcodeFormat.EAN_13,
                widthPixels,
                heightPixels
            )

            val pixels = IntArray(bitMatrix.width * bitMatrix.height)
            for (y in 0 until bitMatrix.height) {
                val offset = y * bitMatrix.width
                for (x in 0 until bitMatrix.width) {
                    pixels[offset + x] =
                        if (bitMatrix.get(x, y)) barcodeColor else backgroundColor
                }
            }

            val bitmap = Bitmap.createBitmap(
                bitMatrix.width,
                bitMatrix.height,
                Bitmap.Config.ARGB_8888
            )
            bitmap.setPixels(
                pixels,
                0,
                bitMatrix.width,
                0,
                0,
                bitMatrix.width,
                bitMatrix.height
            )
            return bitmap
        }

    }
}