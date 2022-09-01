package com.example.cardlinker.presentation.base.code_creator

import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.util.Xml
import com.example.cardlinker.domain.models.Code
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.qrcode.QRCodeWriter
import java.util.*
import kotlin.collections.ArrayList

class CodeCreator {
    companion object {
        fun createBarcodeBitmap(
            code: Code,
            widthPixels: Int,
            heightPixels: Int
        ): Bitmap {
            println(code)
            val finalData = Uri.decode(code.data)
            println(finalData)
            val bitMatrix = MultiFormatWriter().encode(
                finalData,
                code.barcodeFormat,
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

        fun createQrCodeBitmap(
            code: Code,
            widthPixels: Int,
            heightPixels: Int,
        ): Bitmap {
            val qrCodeContent = code.data
            val bits = QRCodeWriter().encode(qrCodeContent, BarcodeFormat.QR_CODE, widthPixels, heightPixels)
            return Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.RGB_565).also {
                for (x in 0 until widthPixels) {
                    for (y in 0 until heightPixels) {
                        it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                    }
                }
            }
        }
    }
}