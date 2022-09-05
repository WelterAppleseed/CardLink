package com.example.cardlinker.presentation.fragments.usercards.card_initializing

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardlinker.R
import com.example.cardlinker.databinding.FragmentCardInitializingBinding
import com.example.cardlinker.domain.models.Card
import com.example.cardlinker.domain.models.Code
import com.example.cardlinker.presentation.base.BaseFragment
import com.example.cardlinker.presentation.base.code_creator.CodeCreator
import com.example.cardlinker.presentation.fragments.usercards.CardBackground
import com.example.cardlinker.presentation.fragments.usercards.card_initializing.styles.CardStylesAdapter
import com.example.cardlinker.presentation.fragments.usercards.card_initializing.styles.OnStyleClickedListener
import com.example.cardlinker.presentation.vm.AccountViewModel
import com.example.cardlinker.presentation.vm.NavigationViewModel
import com.example.cardlinker.presentation.vm.UserAppearanceViewModel
import com.example.cardlinker.presentation.vm.UserCardsViewModel
import com.example.cardlinker.util.codeWithSpaces
import com.example.cardlinker.util.objects.Constants
import com.example.cardlinker.util.objects.Styles
import com.google.zxing.BarcodeFormat
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class CardInitializingFragment :
    BaseFragment<FragmentCardInitializingBinding>(FragmentCardInitializingBinding::inflate),
    OnStyleClickedListener {
    private val cardsViewModel: UserCardsViewModel by activityViewModels()
    private val navigationViewModel: NavigationViewModel by activityViewModels()
    private val userAppearanceViewModel: UserAppearanceViewModel by activityViewModels()
    private val accountViewModel: AccountViewModel by activityViewModels()
    private var cardBackground = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        userAppearanceViewModel.getIsLoggedIn().observe(viewLifecycleOwner) {
            cardsViewModel.getCode().observe(viewLifecycleOwner) { code ->
                if (code != null) {
                    if (it) {
                        accountViewModel.getAccount().observe(viewLifecycleOwner) { account ->
                            if (account != null) {
                                cardsViewModel.initLinkedCards(account.hashCode())
                                cardsViewModel.getLinkedCards()
                                    .observe(viewLifecycleOwner) { cards ->
                                        cardInitializing(code, cards, account.hashCode())
                                    }
                            }
                        }
                    } else {
                        cardsViewModel.getNotLinkedCards()
                            .observe(viewLifecycleOwner) { cards ->
                                cardInitializing(code, cards, Constants.ACCOUNT_IS_NULL)
                            }
                    }
                }
            }
        }
    }

    private fun cardInitializing(code: Code, cards: List<Card>, accountHashCode: Int) {
        binding.apply {
            for (card in cards) {
                println("$card $cards ${code.data}")
                if (card.code.data == code.data) {
                    changeToolbar(code, (accountHashCode != Constants.ACCOUNT_IS_NULL))
                    acceptB.visibility = View.GONE
                    discountCardView.setNonEditableState()
                    problemWithCardLayout.root.visibility = View.VISIBLE
                    problemWithCardLayout.contactB.setOnClickListener {
                        //TODO
                    }
                    initCardData(card)
                    break
                }
            }
            displayCode(code)
            if (stylesRecycler.visibility == View.VISIBLE) {
                initCardData(code)
            }
            OverScrollDecoratorHelper.setUpOverScroll(initCardNestedScrollview)
            context?.let { it1 -> getColor(it1, R.color.toolbar_background_color) }
                ?.let { it2 -> drawStatusBar(it2, false) }
            acceptB.setOnClickListener {
                codeLayout.apply {
                    cardsViewModel.saveCard(
                        Card(
                            name = cardTitleTv.text.toString(),
                            code = code,
                            number = code.data,
                            background = cardBackground,
                            style = discountCardView.getStyle(),
                            accountHashCode = accountHashCode
                        ),
                        (accountHashCode != Constants.ACCOUNT_IS_NULL)
                    )
                    navigationViewModel.goToUserCardsFragment()
                }
            }
        }
    }

    private fun initStylesRecycler() {
        binding.apply {
            stylesRecycler.adapter = CardStylesAdapter(this@CardInitializingFragment)
            stylesRecycler.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun changeToolbar(code: Code, isLinked: Boolean) {
        binding.apply {
            cardToolbar.menu.clear()
            cardToolbar.inflateMenu(R.menu.init_close_menu)
            cardToolbar.setOnMenuItemClickListener {
                if (it.itemId == R.id.action_delete) {
                    cardsViewModel.deleteCard(code.data, isLinked)
                    navigationViewModel.goToUserCardsFragment()
                    return@setOnMenuItemClickListener true
                }
                return@setOnMenuItemClickListener false

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cardsViewModel.deleteCode()
    }

    private fun initCardData(card: Card) {
        binding.apply {
            card.name?.let { initToolbarMenu(it) }
            card.name?.let { discountCardView.setCardName(it) }
            stylesRecycler.visibility = View.GONE
            if (card.style == null) {
                discountCardView.setSrc(card.background)
                discountCardView.hideCardName()
            } else {
                discountCardView.setForegroundVisibility(View.VISIBLE)
                discountCardView.changeStyle(card.style)
            }
            codeLayout.apply {
                bottomCodeTv.text = card.number?.codeWithSpaces()
                cardNumTv.text = card.number
                cardTitleTv.text = card.name
            }
        }
    }

    private fun initCardData(code: Code) {
        binding.apply {
            val pair = CardBackground.getSrcAndNameIfExist(code.data)
            println(pair)
            if (pair != null) {
                stylesRecycler.visibility = View.GONE
                discountCardView.setSrc(pair.first)
                discountCardView.setCardName(pair.second)
                initToolbarMenu(pair.second)
                codeLayout.cardTitleTv.text = pair.second
                cardBackground = pair.first
            } else {
                initStylesRecycler()
                discountCardView.changeStyle(Styles.MARKET_STYLE)
                discountCardView.connectWithTextView(codeLayout.cardTitleTv)
                discountCardView.ifCardRecognizeError()
                cardBackground = R.drawable.card_field
            }
            codeLayout.apply {
                bottomCodeTv.text = code.data.codeWithSpaces()
                cardNumTv.text = code.data
            }
        }
    }

    private fun initToolbar() {
        binding.apply {
            cardToolbar.setNavigationOnClickListener {
                navigationViewModel.goToUserCardsFragment()
            }
        }
    }

    private fun initToolbarMenu(name: String) {
        binding.apply {
            binding.cardToolbarTitle.text = name
        }
    }

    private fun displayCode(code: Code) {
        binding.apply {
            when (code.barcodeFormat) {
                BarcodeFormat.QR_CODE -> {
                    val widthPixels = resources.getDimensionPixelSize(R.dimen.qr_code_side_size)
                    val heightPixels = resources.getDimensionPixelSize(R.dimen.qr_code_side_size)
                    codeLayout.codeIv.setImageBitmap(
                        CodeCreator.createQrCodeBitmap(
                            code = code,
                            widthPixels = widthPixels,
                            heightPixels = heightPixels
                        )
                    )
                }
                BarcodeFormat.EAN_13 -> {
                    val widthPixels = resources.getDimensionPixelSize(R.dimen.barcode_width)
                    val heightPixels = resources.getDimensionPixelSize(R.dimen.barcode_height)
                    codeLayout.codeIv.setImageBitmap(
                        CodeCreator.createBarcodeBitmap(
                            code = code,
                            widthPixels = widthPixels,
                            heightPixels = heightPixels
                        )
                    )
                }
                else -> {}
            }
            codeLayout.bottomCodeTv.text = code.data.codeWithSpaces()
        }
    }

    override var bottomNavigationViewVisibility: Int = View.GONE

    override fun onStyleClicked(styleName: String) {
        binding.apply {
            when (styleName) {
                Styles.MARKET_STYLE.styleName -> {
                    discountCardView.changeStyle(Styles.MARKET_STYLE)
                }
                Styles.TECH_STYLE.styleName -> {
                    discountCardView.changeStyle(Styles.TECH_STYLE)
                }
                Styles.COSMETIC_STYLE.styleName -> {
                    discountCardView.changeStyle(Styles.COSMETIC_STYLE)
                }
                Styles.CLOTHES_STYLE.styleName -> {
                    discountCardView.changeStyle(Styles.CLOTHES_STYLE)
                }
            }
        }
    }
}