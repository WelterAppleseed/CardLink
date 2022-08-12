package com.example.cardlinker.presentation.fragments.usercards

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
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
import com.example.cardlinker.domain.models.Recommendation
import com.example.cardlinker.presentation.base.BaseFragment
import com.example.cardlinker.presentation.base.codeformatter.OnCodeFormattedListener
import com.example.cardlinker.presentation.base.image_picker.ImagePicker
import com.example.cardlinker.presentation.base.text_watchers.OnCardClickListener
import com.example.cardlinker.presentation.fragments.usercards.recommendations.OnRecommendationListener
import com.example.cardlinker.presentation.fragments.usercards.recommendations.RecommendationAdapter
import com.example.cardlinker.presentation.vm.NavigationViewModel
import com.example.cardlinker.presentation.vm.RecommendationViewModel
import com.example.cardlinker.presentation.vm.UserCardsViewModel
import com.google.mlkit.vision.barcode.common.Barcode
import dagger.hilt.android.AndroidEntryPoint
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

@AndroidEntryPoint
class UserCardsFragment :
    BaseFragment<FragmentWalletLayoutBinding>(FragmentWalletLayoutBinding::inflate),
    OnCodeFormattedListener, OnCardClickListener, OnRecommendationListener {
    private val cardsViewModel: UserCardsViewModel by activityViewModels()
    private val navigationViewModel: NavigationViewModel by activityViewModels()
    private val recommendationViewModel: RecommendationViewModel by activityViewModels()
    private val imagePicker = ImagePicker(this, this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCardRecycler()
        initRecommendationRecycler()
    }
    private fun initCardRecycler() {
        cardsViewModel.getCards().observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    myCardRecycler.adapter = UserCardsAdapter(it, this@UserCardsFragment)
                    myCardRecycler.layoutManager = GridLayoutManager(context, 2)
                    if (it.size > 6)  OverScrollDecoratorHelper.setUpOverScroll(myCardRecycler, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)
                    initToolbar()
                }
            }
        }
    }
    private fun initRecommendationRecycler() {
        recommendationViewModel.getRecommendations().observe(viewLifecycleOwner) {
            binding.horizontalRecommendationLayout.apply {
                recommendationRecycler.adapter = RecommendationAdapter(it, this@UserCardsFragment)
                recommendationRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }
    private fun initToolbar() {
        binding.apply {
            searchView.setOnFocusChangeListener({
                myCardTv.visibility = View.GONE
                horizontalRecommendationLayout.root.visibility = View.GONE
                recyclerLayout.backgroundTintList = ColorStateList.valueOf(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.theme_background_color,
                        null
                    )
                )
                searchView.layoutParams = Toolbar.LayoutParams(resources.getDimension(R.dimen.search_field_part_width).toInt(),Toolbar.LayoutParams.WRAP_CONTENT)
                cancelTv.visibility = View.VISIBLE
                searchAddBar.menu.getItem(0).isEnabled = false
                searchAddBar.menu.getItem(0).isVisible = false
            }, {
                myCardTv.visibility = View.VISIBLE
                recyclerLayout.backgroundTintList = null
                searchView.layoutParams = Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT)
                cancelTv.visibility = View.GONE
                searchAddBar.menu.getItem(0).isEnabled = true
                searchAddBar.menu.getItem(0).isVisible = true
                horizontalRecommendationLayout.root.visibility = View.VISIBLE
            })
            searchView.setOnTextChangedListener { changedPart ->
                (myCardRecycler.adapter as UserCardsAdapter).filter(changedPart)
            }
            cancelTv.setOnClickListener {
                searchView.removeFocus()
                hideKeyboard()
            }
            context?.let { it1 -> ContextCompat.getColor(it1, R.color.theme_background_color) }
                ?.let { it2 -> drawStatusBar(it2, false) }
            val dialog =
                createCustomDialog(SelectImageDialogBinding.inflate(layoutInflater)) { viewBinding, dialog ->
                    if (viewBinding is SelectImageDialogBinding) {
                        viewBinding.fromGalleryTv.setOnClickListener {
                            dialog.dismiss()
                            imagePicker.startPickingImageFromGallery()
                        }
                        viewBinding.fromPhotoTv.setOnClickListener {
                            dialog.dismiss()
                            navigationViewModel.goToCameraFragment()
                        }
                    }
                }
            searchAddBar.apply {
                val addIcon = menu.findItem(R.id.action_add)
                setOnMenuItemClickListener {
                    if (it == addIcon) {
                        dialog.show()
                    }
                    return@setOnMenuItemClickListener true
                }
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

    override fun onRecommendationClicked(recommendation: Recommendation) {
        println(recommendation.id)
    }
}