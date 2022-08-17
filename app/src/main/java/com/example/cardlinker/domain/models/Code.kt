package com.example.cardlinker.domain.models

import com.google.zxing.BarcodeFormat
import com.google.zxing.ResultMetadataType
import java.nio.charset.Charset


data class Code(
    val charset: String,
    val barcodeFormat: BarcodeFormat,
    val metaData: Map<ResultMetadataType, Any>?,
    val data: String
)