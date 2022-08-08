package com.example.cardlinker.presentation.base.image_picker

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment


class ImagePicker(fragment: Fragment) {
    companion object {
        private const val CAMERA_REQUEST = 1888
        private const val MY_CAMERA_PERMISSION_CODE = 100
        private const val SELECT_PICTURE = 1
    }
    private var activityResultLauncher: ActivityResultLauncher<Intent>? = null
    private var bitmapImage: Bitmap? = null
    private var uriImage: Uri? = null
    init {
        if (activityResultLauncher == null) {
            activityResultLauncher =
                fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                    val requestCode = result.data?.extras?.getInt("REQUEST_CODE")
                    if (result.resultCode == Activity.RESULT_OK) {
                        when (requestCode) {
                            CAMERA_REQUEST -> {
                                bitmapImage = result?.data?.extras?.get("data") as Bitmap
                            }
                            SELECT_PICTURE -> {
                                uriImage = result?.data?.extras?.get("data") as Uri
                            }
                        }
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
            activity.startActivityForResult(cameraIntent, CAMERA_REQUEST)
        }
    }

    fun startPickingImageFromGallery(activity: Activity?) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra("REQUEST_CODE", SELECT_PICTURE)
        activity?.startActivityForResult(
            Intent.createChooser(
                intent,
                "Select Picture"
            ), SELECT_PICTURE
        );
    }
}