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
import com.example.cardlinker.presentation.vm.NavigationViewModel
import com.example.cardlinker.presentation.vm.UserCardsViewModel

class CardInitializingFragment: BaseFragment<FragmentCardInitializingBinding>(FragmentCardInitializingBinding::inflate) {
    private val cardsViewModel: UserCardsViewModel by activityViewModels()
    private val navigationViewModel: NavigationViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardsViewModel.getCode().observe(viewLifecycleOwner) {
            binding.apply {
                displayCode(it)

            }
        }
    }
    private fun displayCode(code: String) {
        binding.apply {
            val widthPixels = resources.getDimensionPixelSize(R.dimen.code_width)
            val heightPixels = resources.getDimensionPixelSize(R.dimen.code_height)
            codeLayout.codeIv.setImageBitmap(
                CodeCreator.createBarcodeBitmap(
                    barcodeValue = code,
                    barcodeColor = getColor(requireContext(), R.color.black),
                    backgroundColor = getColor(requireContext(), R.color.white),
                    widthPixels = widthPixels,
                    heightPixels = heightPixels
                )
            )
        }
    }
}