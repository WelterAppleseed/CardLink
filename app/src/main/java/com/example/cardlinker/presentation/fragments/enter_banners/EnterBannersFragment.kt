package com.example.cardlinker.presentation.fragments.enter_banners

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.cardlinker.R
import com.example.cardlinker.databinding.FragmentEnterBannersBinding
import com.example.cardlinker.presentation.base.BaseFragment
import com.example.cardlinker.presentation.vm.NavigationViewModel
import com.example.cardlinker.presentation.vm.UserAppearanceViewModel
import com.example.cardlinker.util.objects.EnterBanners
import com.example.cardlinker.util.disableClickEvent
import com.example.cardlinker.util.enableClickEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnterBannersFragment :
    BaseFragment<FragmentEnterBannersBinding>(FragmentEnterBannersBinding::inflate) {
    private val navigationViewModel: NavigationViewModel by activityViewModels()
    private val userAppearanceViewModel: UserAppearanceViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewPager()
    }

    private fun initializeViewPager() {
        binding.apply {
            var currentPosition = 1
            nextButton.setOnClickListener {
                currentPosition++
                changePage(currentPosition, EnterBanners.getBanners().size-1)
            }
            skipTv.setOnClickListener {
                navigationViewModel.goToUserCardsFragment()
            }
            bannerViewPager.adapter = EnterBannersAdapter(
                EnterBanners.getBanners()
            )
            bannerViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            circleIndicator.setViewPager(bannerViewPager)
            bannerViewPager.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    currentPosition = position
                    onPageChange(position)
                }
            })
        }
    }
    private fun onPageChange(position: Int) {
        binding.apply {
            if (position == EnterBanners.getBanners().size - 1) {
                skipTv.visibility = View.INVISIBLE
                skipTv.disableClickEvent()
                nextButton.setText(R.string.start)
            } else {
                skipTv.visibility = View.VISIBLE
                skipTv.enableClickEvent()
                nextButton.setText(R.string.next)
            }
        }
    }
    private fun changePage(currentPosition: Int, lastPosition: Int) {
        binding.apply {
            if (currentPosition in 0..lastPosition) bannerViewPager.currentItem = currentPosition else {
                userAppearanceViewModel.changeFirstTimeUsedState(false)
                navigationViewModel.goToUserCardsFragment()
            }
        }
    }

    override var bottomNavigationViewVisibility: Int = View.GONE
    override var topBarColor: Int = R.color.theme_background_color

}