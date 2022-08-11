package com.example.cardlinker.presentation.base.code_creator

import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import androidx.annotation.ColorInt
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.oned.CodaBarWriter
import java.util.*

class CodeCreator {
    companion object {
        fun createBarcodeBitmap(
            barcodeValue: String,
            widthPixels: Int,
            heightPixels: Int,
        ): Bitmap {
            val finalData = Uri.encode(barcodeValue)
            println("$finalData $barcodeValue $widthPixels $heightPixels")
            val bitMatrix = MultiFormatWriter().encode(
                finalData,
                BarcodeFormat.EAN_13,
                widthPixels,
                1,
            )
            val width = bitMatrix.width
            val imageBitmap = Bitmap.createBitmap(width, heightPixels, Bitmap.Config.ARGB_8888)
            for (i in 0 until width) {
                val column = IntArray(heightPixels)
                Arrays.fill(column, if (bitMatrix.get(i, 0)) Color.BLACK else Color.WHITE)
                imageBitmap.setPixels(column, 0, 1, i, 0, 1, heightPixels)
            }
            return imageBitmap
        }
    }
}