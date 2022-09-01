package com.example.cardlinker.presentation.base.custom

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.cardlinker.R
import com.example.cardlinker.presentation.base.code_creator.CodeReader
import com.example.cardlinker.presentation.base.codeformatter.CodeFormatter
import com.example.cardlinker.presentation.base.codeformatter.OnCodeFormattedListener
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class CameraView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private val viewFinder: PreviewView
    private lateinit var imageCapture: ImageCapture
    private var uris = Pair<Uri?, Uri?>(null, null)

    companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private val TAG = "CameraProviderException"
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.camera_layout, this, true)
        viewFinder = findViewById(R.id.view_finder)
    }

    fun startCamera(viewLifecycleOwner: LifecycleOwner) {
        grant()
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewFinder.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY).build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    viewLifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (e: Exception) {
                Log.e(TAG, "Use case binding failed", e)
            }
        }, ContextCompat.getMainExecutor(context))
    }

    private fun grant() {
        ActivityCompat.requestPermissions(
            (context as Activity?)!!, arrayOf(Manifest.permission.CAMERA),
            100
        )
    }

    fun cameraPermissionsGranted(context: Context?): Boolean {
        return if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("checkCameraPermissions", "No Camera Permissions")
            ActivityCompat.requestPermissions(
                (context as Activity?)!!, arrayOf(Manifest.permission.CAMERA),
                100
            )
            true
        } else {
            false
        }
    }

    fun takePhoto(outputDirectory: File?, onCodeFormattedListener: OnCodeFormattedListener) {
        if (outputDirectory != null) {
            val imageCapture = imageCapture
            val photoFile = File(
                outputDirectory,
                SimpleDateFormat(
                    FILENAME_FORMAT,
                    Locale.US
                ).format(System.currentTimeMillis()) + ".jpg"
            )
            viewFinder.foreground = BitmapDrawable(resources, viewFinder.bitmap)
            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
            imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        val savedUri = Uri.fromFile(photoFile)
                        uris = if (uris.first == null) {
                            savedUri to null
                        } else {
                            uris.first to savedUri
                        }
                        onCodeFormattedListener.onCodeFormatted(uris.first?.let { CodeReader.readImage(it, context.contentResolver) })
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Log.e(TAG, "Photo capture failed: ${exception.message}", exception)
                    }

                }

            )
        }
    }
    fun clearForeground() {
        viewFinder.foreground = null
    }
    fun getUris(): Pair<Uri?, Uri?> {
        return uris
    }
}