package com.example.cardlinker.presentation.fragments.usercards.camera

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.cardlinker.R
import com.example.cardlinker.databinding.FragmentCameraBinding
import com.example.cardlinker.databinding.InitializationErrorDialogBinding
import com.example.cardlinker.presentation.base.BaseFragment
import com.example.cardlinker.presentation.base.codeformatter.CodeFormatter
import com.example.cardlinker.presentation.base.codeformatter.OnCodeFormattedListener
import com.example.cardlinker.presentation.vm.NavigationViewModel
import com.example.cardlinker.presentation.vm.UserCardsViewModel
import com.google.mlkit.vision.barcode.common.Barcode
import java.io.File

class CameraFragment: BaseFragment<FragmentCameraBinding>(FragmentCameraBinding::inflate), OnCodeFormattedListener {
    private val cardsViewModel: UserCardsViewModel by activityViewModels()
    private val navigationViewModel: NavigationViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            cameraView.startCamera(viewLifecycleOwner)
            captureIv.setOnClickListener {
                cameraView.takePhoto(getOutputDirectory(), this@CameraFragment)
            }
        }
    }
    private fun getOutputDirectory(): File? {
        val mediaDir = activity?.externalMediaDirs?.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else activity?.filesDir
    }
    private fun showDialog() {
        createCustomDialog(InitializationErrorDialogBinding.inflate(layoutInflater)) { thisViewBinding, dialog ->
            if (thisViewBinding is InitializationErrorDialogBinding) {
                thisViewBinding.errorCloseB.setOnClickListener{
                    dialog.dismiss()
                }
            }
        }.show()
    }
    override fun onCodeFormatted(codes: List<Barcode>) {
        for (code in codes) {
            if (code.rawValue != null) {
                cardsViewModel.saveCode(code.rawValue!!)
                navigationViewModel.goToCardInitializingFragment()
                break
            }
        }
        if (codes.isEmpty()) {
            showDialog()
            navigationViewModel.goToUserCardsFragment()
        }
    }

    override var bottomNavigationViewVisibility: Int = View.GONE

}