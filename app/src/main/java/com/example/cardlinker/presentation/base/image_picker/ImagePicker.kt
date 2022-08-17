package com.example.cardlinker.presentation.base.image_picker

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.cardlinker.presentation.base.code_creator.CodeReader
import com.example.cardlinker.presentation.base.codeformatter.CodeFormatter
import com.example.cardlinker.presentation.base.codeformatter.OnCodeFormattedListener


class ImagePicker(fragment: Fragment, onCodeFormatted: OnCodeFormattedListener) {
    companion object {
        private const val CAMERA_REQUEST = 1888
        private const val MY_CAMERA_PERMISSION_CODE = 100
        private const val SELECT_PICTURE = 1
    }

    private var bitmapResultLauncher: ActivityResultLauncher<Intent>? = null
    private var uriResultLauncher: ActivityResultLauncher<Intent>? = null
    private var bitmapImage: Bitmap? = null
    private var uriImage: Uri? = null

    init {
        if (uriResultLauncher == null) {
            uriResultLauncher =
                fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                    if (result.resultCode == Activity.RESULT_OK) {
                        uriImage = result?.data?.data
                        uriImage?.let { CodeReader.readImage(it, fragment.requireContext().contentResolver) }
                            ?.let { onCodeFormatted.onCodeFormatted(it) }
                    }
                }
        }
        if (bitmapResultLauncher == null) {
            bitmapResultLauncher =
                fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                    if (result.resultCode == Activity.RESULT_OK) {
                        bitmapImage = result?.data?.extras?.get("data") as Bitmap
                        bitmapImage?.let { CodeReader.readImage(it) }
                            ?.let { onCodeFormatted.onCodeFormatted(it) }
                    }
                }

        }
    }

    fun startPickingImageFromCamera(activity: Activity?) {
        if (activity?.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            activity?.requestPermissions(
                arrayOf(Manifest.permission.CAMERA),
                MY_CAMERA_PERMISSION_CODE
            )
        } else {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraIntent.putExtra("REQUEST_CODE", CAMERA_REQUEST)
            uriResultLauncher?.launch(cameraIntent)
        }
    }

    fun startPickingImageFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra("REQUEST_CODE", SELECT_PICTURE)
        uriResultLauncher?.launch(intent)
    }
}