package com.example.cardlinker.presentation.fragments.usercards

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardlinker.R
import com.example.cardlinker.databinding.FragmentWalletLayoutBinding
import com.example.cardlinker.databinding.InitializationErrorDialogBinding
import com.example.cardlinker.databinding.SelectImageDialogBinding
import com.example.cardlinker.domain.models.Card
import com.example.cardlinker.domain.models.Code
import com.example.cardlinker.presentation.base.BaseFragment
import com.example.cardlinker.presentation.base.codeformatter.OnCodeFormattedListener
import com.example.cardlinker.presentation.base.image_picker.ImagePicker
import com.example.cardlinker.presentation.base.text_watchers.OnCardClickListener
import com.example.cardlinker.presentation.fragments.usercards.recommendations.RecommendationAdapter
import com.example.cardlinker.presentation.vm.NavigationViewModel
import com.example.cardlinker.presentation.vm.RecommendationViewModel
import com.example.cardlinker.presentation.vm.UserAppearanceViewModel
import com.example.cardlinker.presentation.vm.UserCardsViewModel
import com.example.cardlinker.util.objects.BalloonConstants
import com.google.mlkit.vision.barcode.common.Barcode
import com.skydoves.balloon.ArrowOrientation
import dagger.hilt.android.AndroidEntryPoint
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

@AndroidEntryPoint
class UserCardsFragment :
    BaseFragment<FragmentWalletLayoutBinding>(FragmentWalletLayoutBinding::inflate),
    OnCodeFormattedListener, OnCardClickListener {
    private val cardsViewModel: UserCardsViewModel by activityViewModels()
    private val navigationViewModel: NavigationViewModel by activityViewModels()
    private val recommendationViewModel: RecommendationViewModel by activityViewModels()
    private val userAppearanceViewModel: UserAppearanceViewModel by activityViewModels()
    private val imagePicker = ImagePicker(this, this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFirstTimeOnFragmentSituation()
        initRecommendationRecycler()
        initCardRecycler()
        initFirstTimeOnFragmentSituation()
    }

    private fun initCardRecycler() {
        cardsViewModel.getCards().observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null && it.isNotEmpty()) {
                    noCardLayout.root.visibility = View.GONE
                    myCardRecycler.adapter = UserCardsAdapter(it, this@UserCardsFragment)
                    myCardRecycler.layoutManager = GridLayoutManager(context, 2)
                    if (it.size > 6) OverScrollDecoratorHelper.setUpOverScroll(
                        myCardRecycler,
                        OverScrollDecoratorHelper.ORIENTATION_VERTICAL
                    )
                } else {
                    noCardLayout.root.visibility = View.VISIBLE
                }
                initToolbar()
            }
        }
    }
    private fun initFirstTimeOnFragmentSituation() {
        userAppearanceViewModel.checkFirstTimeOnFragment(binding.javaClass.name)
        userAppearanceViewModel.getIsFirstTimeOnFragment().observe(viewLifecycleOwner) {
            if (it) {
                binding.apply {
                    myCardTv.visibility = View.GONE
                    noCardLayout.root.visibility = View.VISIBLE
                    displayTooltip()
                }
            }
        }
    }
    private fun displayTooltip() {
        getToolTip(context?.getString(R.string.tooltip_add_cards), ArrowOrientation.TOP, BalloonConstants.ARROW_POSITION_WITH_CORNERS)?.showAlignBottom(
            binding.placeholderL,
            binding.searchView.layoutParams.width / 2
        )
    }


    private fun initRecommendationRecycler() {
        recommendationViewModel.getRecommendations().observe(viewLifecycleOwner) {
            binding.horizontalRecommendationLayout.apply {
                recommendationRecycler.adapter = RecommendationAdapter(it)
                recommendationRecycler.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                moreTv.setOnClickListener { navigationViewModel.goToSupportedCardsFragment() }
            }
        }
    }

    private fun initToolbar() {
        binding.apply {
            searchView.setOnFocusChangeListener({
                recyclerLayout.backgroundTintList = ColorStateList.valueOf(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.theme_background_color,
                        null
                    )
                )
                searchAddBar.menu.clear()
                searchAddBar.inflateMenu(R.menu.search_menu_short)
                myCardTv.visibility = View.GONE
                horizontalRecommendationLayout.root.visibility = View.GONE
            }, {
                recyclerLayout.backgroundTintList = null
                searchAddBar.menu.clear()
                searchAddBar.inflateMenu(R.menu.search_menu)
                myCardTv.visibility = View.VISIBLE
                horizontalRecommendationLayout.root.visibility = View.VISIBLE
            })
            searchView.setOnTextChangedListener { changedPart ->
                (myCardRecycler.adapter as UserCardsAdapter).filter(changedPart)
            }
            context?.let { it1 -> ContextCompat.getColor(it1, R.color.theme_background_color) }
                ?.let { it2 -> drawStatusBar(it2, false) }
            val dialog =
                createCustomDialog(SelectImageDialogBinding.inflate(layoutInflater)) { viewBinding, dialog ->
                    if (viewBinding is SelectImageDialogBinding) {
                        viewBinding.fromGalleryTv.setOnClickListener {
                            dialog.dismiss()
                            imagePicker.startPickingImageFromGallery()
                            userAppearanceViewModel.updateFirstTimeOnFragmentState()
                        }
                        viewBinding.fromPhotoTv.setOnClickListener {
                            dialog.dismiss()
                            navigationViewModel.goToCameraFragment()
                            userAppearanceViewModel.updateFirstTimeOnFragmentState()
                        }
                    }
                }
            searchAddBar.apply {
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.action_add -> dialog.show()
                        R.id.action_cancel -> {
                            searchView.removeFocus()
                            hideKeyboard()
                        }
                    }
                    return@setOnMenuItemClickListener true
                }
            }
        }
    }

    override fun onCardClicked(card: Card) {
        navigationViewModel.goToCardInitializingFragment()
        cardsViewModel.saveCode(card.code)
    }

    override fun onCodeFormatted(code: Code?) {
        if (code?.data != null) {
            navigationViewModel.goToCardInitializingFragment()
            cardsViewModel.saveCode(code)
        }
        if (code == null) {
            createCustomDialog(InitializationErrorDialogBinding.inflate(layoutInflater)) { thisViewBinding, dialog ->
                if (thisViewBinding is InitializationErrorDialogBinding) {
                    thisViewBinding.errorCloseB.setOnClickListener {
                        dialog.dismiss()
                    }
                }
            }.show()
        }
    }
}