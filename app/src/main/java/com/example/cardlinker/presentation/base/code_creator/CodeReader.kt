package com.example.cardlinker.presentation.base.code_creator

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.example.cardlinker.domain.models.Code
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.common.HybridBinarizer


class CodeReader {
    companion object {
        fun readImage(uri: Uri, contentResolver: ContentResolver): Code? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(contentResolver, uri)
                readImage(ImageDecoder.decodeBitmap(source).copy(Bitmap.Config.RGBA_F16, true))
            } else {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                readImage(bitmap)
            }
        }

        fun readImage(bitmap: Bitmap): Code? {
            val intArray = IntArray(bitmap.width * bitmap.height)
            bitmap.getPixels(intArray, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
            val source = RGBLuminanceSource(bitmap.width, bitmap.height, intArray)
            val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
            return detectCode(binaryBitmap)

        }

        private fun detectCode(binaryBitmap: BinaryBitmap): Code? {
            var code: Code? = null
            val reader = MultiFormatReader()
            try {
                val result = reader.decode(binaryBitmap)
                code = Code("", result.barcodeFormat, result.resultMetadata, result.text)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            println(code)
            return code
        }
    }
}