package com.example.cardlinker.presentation.fragments.usercards.card_initializing

import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.activityViewModels
import com.example.cardlinker.R
import com.example.cardlinker.databinding.FragmentCardInitializingBinding
import com.example.cardlinker.domain.models.Card
import com.example.cardlinker.presentation.base.BaseFragment
import com.example.cardlinker.presentation.base.code_creator.CodeCreator
import com.example.cardlinker.presentation.base.text_watchers.CardTextWatcher
import com.example.cardlinker.presentation.fragments.usercards.CardBackground
import com.example.cardlinker.presentation.vm.NavigationViewModel
import com.example.cardlinker.presentation.vm.UserCardsViewModel
import com.example.cardlinker.util.codeWithSpaces
import com.google.zxing.EncodeHintType
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import java.util.*

class CardInitializingFragment :
    BaseFragment<FragmentCardInitializingBinding>(FragmentCardInitializingBinding::inflate) {
    private val cardsViewModel: UserCardsViewModel by activityViewModels()
    private val navigationViewModel: NavigationViewModel by activityViewModels()
    private var cardBackground = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardsViewModel.getCode().observe(viewLifecycleOwner) { code ->
            binding.apply {
                if (code != null) {
                    displayCode(code)
                    initCardData(code)
                    OverScrollDecoratorHelper.setUpOverScroll(initCardNestedScrollview)
                    context?.let { it1 -> getColor(it1, R.color.toolbar_background_color) }
                        ?.let { it2 -> drawStatusBar(it2, false) }
                    acceptB.setOnClickListener {
                        codeLayout.apply {
                            cardsViewModel.saveCard(
                                Card(
                                    name = cardTitleTv.text.toString(),
                                    barcode = code,
                                    number = code,
                                    background = cardBackground
                                )
                            )
                            navigationViewModel.goToUserCardsFragment()
                        }
                    }
                }
            }
        }
    }

    private fun initCardData(cardNumber: String) {
        binding.apply {
            val pair = CardBackground.getSrcAndNameIfExist(cardNumber)
            if (pair != null) {
                discountCardView.setSrcAndName(pair)
                initToolbar(pair.second)
                codeLayout.cardTitleTv.text = pair.second
                cardBackground = pair.first
            } else {
                discountCardView.ifCardRecognizeError()
                cardBackground = R.drawable.card_field
            }
            codeLayout.apply {
                bottomCodeTv.text = cardNumber.codeWithSpaces()
                cardNumTv.text = cardNumber
            }
        }
    }
    private fun initToolbar(title: String) {
        binding.apply {
            cardToolbar.setNavigationOnClickListener {
                navigationViewModel.goToUserCardsFragment()
            }
            binding.cardToolbarTitle.text = title
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