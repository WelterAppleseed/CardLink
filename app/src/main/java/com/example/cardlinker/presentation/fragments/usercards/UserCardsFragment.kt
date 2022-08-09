package com.example.cardlinker.presentation.fragments.usercards

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.cardlinker.R
import com.example.cardlinker.databinding.FragmentWalletLayoutBinding
import com.example.cardlinker.databinding.SelectImageLayoutBinding
import com.example.cardlinker.domain.models.Card
import com.example.cardlinker.presentation.base.BaseFragment
import com.example.cardlinker.presentation.base.codeformatter.OnCodeFormattedListener
import com.example.cardlinker.presentation.base.image_picker.ImagePicker
import com.example.cardlinker.presentation.vm.NavigationViewModel
import com.example.cardlinker.presentation.vm.UserCardsViewModel
import com.google.mlkit.vision.barcode.common.Barcode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserCardsFragment: BaseFragment<FragmentWalletLayoutBinding>(FragmentWalletLayoutBinding::inflate), OnCodeFormattedListener {
    private val cardsViewModel: UserCardsViewModel by activityViewModels()
    private val navigationViewModel: NavigationViewModel by activityViewModels()
    private val imagePicker = ImagePicker(this, this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
    }

    private fun initToolbar() {
        binding.searchAddBar.apply {
            initCustomDialog(SelectImageLayoutBinding.inflate(layoutInflater)) {
                if (it is SelectImageLayoutBinding) {
                    it.fromGalleryTv.setOnClickListener { imagePicker.startPickingImageFromGallery() }
                    it.fromPhotoTv.setOnClickListener {
                        imagePicker.startPickingImageFromCamera(
                            activity
                        )
                    }
                }
            }
            val addIcon = menu.findItem(R.id.action_add)
            setOnMenuItemClickListener {
                if (it == addIcon) {
                    getCustomDialog()?.show()
                }
                return@setOnMenuItemClickListener true
            }
        }
    }

    override fun onCodeFormatted(codes: List<Barcode>) {
        for (code in codes) {
            if (code.rawValue != null) {
                cardsViewModel.saveCode(code.rawValue!!)
                break
            }
        }
        getCustomDialog()?.dismiss()
        navigationViewModel.goToCardInitializingFragment()
    }
}