package com.example.cardlinker.presentation.fragments.usercards

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cardlinker.R
import com.example.cardlinker.databinding.FragmentWalletLayoutBinding
import com.example.cardlinker.databinding.InitializationErrorDialogBinding
import com.example.cardlinker.databinding.SelectImageDialogBinding
import com.example.cardlinker.domain.models.Card
import com.example.cardlinker.presentation.base.BaseFragment
import com.example.cardlinker.presentation.base.codeformatter.OnCodeFormattedListener
import com.example.cardlinker.presentation.base.image_picker.ImagePicker
import com.example.cardlinker.presentation.base.text_watchers.OnCardClickListener
import com.example.cardlinker.presentation.vm.NavigationViewModel
import com.example.cardlinker.presentation.vm.UserCardsViewModel
import com.google.mlkit.vision.barcode.common.Barcode
import dagger.hilt.android.AndroidEntryPoint
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

@AndroidEntryPoint
class UserCardsFragment :
    BaseFragment<FragmentWalletLayoutBinding>(FragmentWalletLayoutBinding::inflate),
    OnCodeFormattedListener, OnCardClickListener {
    private val cardsViewModel: UserCardsViewModel by activityViewModels()
    private val navigationViewModel: NavigationViewModel by activityViewModels()
    private val imagePicker = ImagePicker(this, this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initCardRecycler()
        println("2")
    }
    private fun initCardRecycler() {
        cardsViewModel.getCards().observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    myCardRecycler.adapter = UserCardsAdapter(it, this@UserCardsFragment)
                    myCardRecycler.layoutManager = GridLayoutManager(context, 2)
                    if (it.size > 6)  OverScrollDecoratorHelper.setUpOverScroll(myCardRecycler, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)
                }
            }
        }
    }
    private fun initToolbar() {
        binding.searchAddBar.apply {
            context?.let { it1 -> ContextCompat.getColor(it1, R.color.theme_background_color) }
                ?.let { it2 -> drawStatusBar(it2, false) }
            val dialog = createCustomDialog(SelectImageDialogBinding.inflate(layoutInflater)) { viewBinding, dialog ->
                if (viewBinding is SelectImageDialogBinding) {
                    viewBinding.fromGalleryTv.setOnClickListener {
                        dialog.dismiss()
                        imagePicker.startPickingImageFromGallery() }
                    viewBinding.fromPhotoTv.setOnClickListener {
                        dialog.dismiss()
                        navigationViewModel.goToCameraFragment()
                    }
                }
            }
            val addIcon = menu.findItem(R.id.action_add)
            setOnMenuItemClickListener {
                if (it == addIcon) {
                    dialog.show()
                }
                return@setOnMenuItemClickListener true
            }
        }
    }
    override fun onCardClicked(card: Card) {
        navigationViewModel.goToCardInitializingFragment()
        cardsViewModel.saveCode(card.barcode)
    }
    override fun onCodeFormatted(codes: List<Barcode>) {
        for (code in codes) {
            if (code.rawValue != null) {
                navigationViewModel.goToCardInitializingFragment()
                cardsViewModel.saveCode(code.rawValue!!)
                break
            }
        }
        if (codes.isEmpty()) {
            createCustomDialog(InitializationErrorDialogBinding.inflate(layoutInflater)) {
                thisViewBinding, dialog ->
                if (thisViewBinding is InitializationErrorDialogBinding) {
                    thisViewBinding.errorCloseB.setOnClickListener{
                        dialog.dismiss()
                    }
                }
            }.show()
        }
    }
}