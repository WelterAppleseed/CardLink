package com.example.cardlinker.presentation.fragments.usercards.card_initializing

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.activityViewModels
import com.example.cardlinker.R
import com.example.cardlinker.databinding.FragmentCardInitializingBinding
import com.example.cardlinker.presentation.base.BaseFragment
import com.example.cardlinker.presentation.base.code_creator.CodeCreator
import com.example.cardlinker.presentation.base.text_watchers.CardTextWatcher
import com.example.cardlinker.presentation.vm.NavigationViewModel
import com.example.cardlinker.presentation.vm.UserCardsViewModel
import com.example.cardlinker.util.codeWithSpaces
import com.google.zxing.EncodeHintType
import java.util.*

class CardInitializingFragment :
    BaseFragment<FragmentCardInitializingBinding>(FragmentCardInitializingBinding::inflate) {
    private val cardsViewModel: UserCardsViewModel by activityViewModels()
    private val navigationViewModel: NavigationViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardsViewModel.getCode().observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    displayCode(it)
                    initCardData(it)

                }
            }
        }
    }

    private fun initCardData(cardNumber: String) {
        binding.apply {
            discountCardView.setCardNumber(cardNumber)
        }
    }

    private fun displayCode(code: String) {
        binding.apply {
            val widthPixels = resources.getDimensionPixelSize(R.dimen.code_width)
            val heightPixels = resources.getDimensionPixelSize(R.dimen.code_height)
            codeLayout.codeIv.setImageBitmap(
                CodeCreator.createBarcodeBitmap(
                    barcodeValue = code,
                    widthPixels = widthPixels,
                    heightPixels = heightPixels
                )
            )
            codeLayout.bottomCodeTv.text = code.codeWithSpaces()
        }
    }
}